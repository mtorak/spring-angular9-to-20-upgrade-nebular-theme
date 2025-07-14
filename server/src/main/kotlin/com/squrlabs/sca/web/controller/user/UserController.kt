package com.squrlabs.sca.web.controller.user

import com.squrlabs.sca.config.auth.util.UserPrincipal
import com.squrlabs.sca.domain.model.user.UploadResponse
import com.squrlabs.sca.domain.service.file.FileStorageService
import com.squrlabs.sca.domain.service.user.UserService
import com.squrlabs.sca.web.controller.user.UserController.Companion.USER_BASE_URI
import com.squrlabs.sca.web.dto.user.UserProfile
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(USER_BASE_URI, consumes = ["application/json"])
@Tag(name = "User Api", description = " This contains url related to login account")
class UserController(
    @Autowired val userService: UserService,
    @Autowired val fileStorageService: FileStorageService
) {

  companion object {
    const val USER_BASE_URI = "/api/user"
  }

  @GetMapping("/me")
  fun getMyProfile(): UserProfile {
    val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
    return this.userService.getUserProfile(user.id).let {
      UserProfile(it.id, it.email, it.name, it.imgUrl)
    }
  }

  @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  fun uploadSingleFile(@RequestParam("file") file: MultipartFile): ResponseEntity<UploadResponse> {
    if (file.isEmpty) {
      return sendBadRequestResponse("Please select a file to upload!", HttpStatus.BAD_REQUEST)
    }

    try {
      val fileName = file.originalFilename ?: "unknown_file"
      val fileUrl = fileStorageService.store(file)

      // update db
      val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
      userService.updateImgUrl(user, fileUrl)

      return ResponseEntity.ok(
          UploadResponse(
              fileName = fileName,
              fileUrl = fileUrl,
              status = HttpStatus.OK.name,
              detail = "Success"))
    } catch (e: Exception) {
      return sendBadRequestResponse(
          "Failed to upload file: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  private fun sendBadRequestResponse(
      detail: String,
      status: HttpStatus
  ): ResponseEntity<UploadResponse> {
    return ResponseEntity.badRequest()
        .body(UploadResponse(fileName = "", fileUrl = "", status = status.name, detail = detail))
  }
}

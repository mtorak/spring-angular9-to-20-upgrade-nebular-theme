import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { UserProfile } from 'src/app/_dtos/user/UserProfile';

import { UntypedFormGroup, UntypedFormBuilder } from "@angular/forms";
import { FileUploader, FileUploaderOptions } from "ng2-file-upload";
import { AuthService } from 'src/app/_services/auth.service';
import { throwError } from 'rxjs';

import { environment } from 'src/environments/environment';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profile: UserProfile

  uploader: FileUploader;
  imageForm: UntypedFormGroup;
  uploadStatus: number;

  constructor(
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private formBuilder: UntypedFormBuilder,
    private authService: AuthService) {

    this.profile = this.userService.getProfile()
    this.imageForm = this.formBuilder.group({});
  }

  ngOnInit(): void {
    this.initializeUploader();
  }

  initializeUploader(): void {

    const token = this.authService.getToken();
    if (!token)
      throwError("A valid JWT token is needed to upload a file!");

    const uploaderOptions: FileUploaderOptions = {
      url: 'http://localhost:8080/api/user/upload',
      autoUpload: true,
      isHTML5: true,
      // Calculate progress independently for each uploaded file
      removeAfterUpload: false,
      headers: [
        {
          name: "X-Requested-With",
          value: "XMLHttpRequest"
        },
        {
          name: "Access-Control-Allow-Origin",
          value: "http://localhost:4200"
        },
        {
          name: "Authorization",
          value: `Bearer ${token}`
        }
      ]
    };

    this.uploader = new FileUploader(uploaderOptions);
    this.uploader.onBuildItemForm = (fileItem: any, form: FormData): any => {
      return { fileItem, form };
    };

    this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
      console.dir(response);

      if (status == 401) {
        this.userService.logout();
      }

      if (status == 200 || status == 201) {
        const newImgUrl = environment.DOMAIN + JSON.parse(response).fileUrl;
        this.profile.imgUrl = newImgUrl;
        this.tokenStorageService.updateImageUrl(newImgUrl);
      }
    };

  }

  continue(): void {
    this.router.navigateByUrl("/chat")
  }

  discard() {
    this.continue();
  }

  fileOverBase($event: any): void {
    // console.dir($event);
  }

}

import { Component, OnInit } from '@angular/core';
import { ChatService } from 'src/app/_services/chat.service';
import { UserService } from 'src/app/_services/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
  standalone: false
})
export class LoadingComponent implements OnInit {

  constructor(
    private chatService: ChatService,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.userService.fetchProfile()
      .subscribe(
        {
          next: (data: any) => {
            this.chatService.updateFetch(10)
          },
          error: (error: any) => {
            console.log(error);
            if (error instanceof HttpErrorResponse && (<HttpErrorResponse>error).status == 401)
              this.userService.logout();
          },
          complete: () => { }
        }
      );

    this.chatService.fetchFriends().subscribe((output) => {
      this.chatService.updateFetch(20)
      this.chatService.fetchAllMessages().subscribe(v => {
        this.chatService.updateFetch(100)
      })
    });

  }

}

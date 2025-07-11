import { Component, Input } from '@angular/core';
import { NbDialogRef, NbToastrService } from '@nebular/theme';
import { ChatService } from 'src/app/_services/chat.service';

@Component({
    template: `
    <nb-card class="dialog-card">
      <nb-card-header>Enter Your Friend Email</nb-card-header>
      <nb-card-body>
        <nb-form-field>
          <input #email nbInput placeholder="Email" type="email" (keyup.enter)="submit(email.value)">
          <button nbSuffix nbButton ghost (click)="email.value=''">
            <nb-icon icon="close-outline"></nb-icon>
          </button>
        </nb-form-field>
      </nb-card-body>
      <nb-card-footer class="text-center">
        <button nbButton (click)="submit(email.value)" status="primary" class="m-2">Submit</button>
        <button nbButton (click)="dismiss()" status="danger" class="m-2">Close</button>
      </nb-card-footer>
    </nb-card>
    `,
    standalone: false
})
export class NewChatComponent {

  constructor(
    protected ref: NbDialogRef<NewChatComponent>,
    private toastrService: NbToastrService,
    private chatService: ChatService,
  ) { }

  dismiss() {
    this.ref.close();
  }

  submit(email: string) {
    if (!email) {
      this.toastrService.show('A valid e-mail must be provided!', 'Invalid Email',
        { status: 'warning', duration: 4000 });
      return;
    } else {
      this.chatService.createFriend(email).subscribe(
        (r) => {
          console.log(r);
          this.ref.close(email);
        },
        (err) => {
          this.toastrService.show(`Couldn\'t create new chat: ${err.error.message}!`, 'Chat Creation Failure',
            { status: 'warning', duration: 4000 });
          return;
        }
      )
    }
  }

}
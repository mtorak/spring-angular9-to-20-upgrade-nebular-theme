import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.scss'],
    standalone: false
})
export class ResetPasswordComponent implements OnInit {

  loading: Boolean = false

  constructor() { }

  ngOnInit(): void {
  }

}

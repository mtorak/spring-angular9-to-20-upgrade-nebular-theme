import { Component, OnInit, VERSION } from '@angular/core';

@Component({
    selector: 'app-chat-banner',
    templateUrl: './chat-banner.component.html',
    styleUrls: ['./chat-banner.component.scss'],
    standalone: false
})
export class ChatBannerComponent implements OnInit {

  angularVersion = VERSION.major + '.' + VERSION.minor;

  constructor() { }

  ngOnInit(): void {
  }

}

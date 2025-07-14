import { Injectable } from '@angular/core';
import { DataService } from './data.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';
import { UserMessage } from '../_dtos/chat/UserMessage';
import { FriendProfile } from '../_dtos/chat/FriendProfile';
import { TokenStorageService } from './token-storage.service';

@Injectable()
export class NotificationService {

  stompClient: any;
  topic: string

  constructor(
    private dataService: DataService,
    private userService: UserService,
    private storageService: TokenStorageService) {

    this.topic = `/notifications/${this.userService.getProfile().id}`
    console.log('Inside NotificationService constructor..');
  }

  suscribe() {

    let ws = new SockJS(`${environment.DOMAIN}/ws`);
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = () => { };
    const _this = this;

    _this.stompClient.connect({ "Authorization": "Bearer " + this.storageService.getToken() },
      function (frame) {
        _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
          _this.onMessageReceived(sdkEvent);
        });
      },
      function (error) {
        setTimeout(() => _this.suscribe(), 5000);
      });

    // on connect handler
    _this.stompClient.onConnect = function (frame) {
      console.log('Broker reported error: ' + frame.headers['message']);
      console.log('Additional details: ' + frame.body);
    };

    // further info: how to publish messages 
    // https://stomp-js.github.io/guide/stompjs/using-stompjs-v5.html

  }

  onMessageReceived(message) {
    let json = JSON.parse(message.body)
    if (json['type'] == "USER_MESSAGE_ADDED") {
      let data = json['data'] as UserMessage
      this.dataService.updateUserMessages([data])
    } else if (json['type'] == "USER_CONVERSATION_UPDATED" || json['type'] == "USER_CONVERSATION_ADDED") {
      let data = json['data'] as FriendProfile
      this.dataService.updateFriends([data])
    } else {
      console.log(json)
    }
  }

}

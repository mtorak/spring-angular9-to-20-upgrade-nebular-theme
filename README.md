# Spring Angular Chat(STOMP)

![Cover Image](./images/chat.png)
1-1 instant messaging project designed to demonstrate WebSockets in a load-balanced environment. Users can register, login/logout, see a friendslist, private message all in realtime. WebSocket usages include user presence monitoring, notifications, and chat messages.

# Update Angular from 9 to 20

### Upgrade from Angular 9 to 20 Case Study
- Update guide: https://angular.dev/update-guide?v=9.0-20.0&l=3
- As a rule of thumb, keep node version greater than Angular version. Example: node version 12 and Angular version 10
- Use even numbered node versions(LTS) 
  
### Update 9 to 10
`nvm use 22`<br>
`npx @angular/cli@10 update @angular/core@10 @angular/cli@10`

***manual update:***
- "@angular/cdk": "^10.2.5"
- "@schematics/angular": "~10.2.4"
- "@nebular/eva-icons": "^6.2.1"
- "@nebular/theme": "^6.2.1"

`nvm use 12`<br>
`npm i`


### Update 10 to 11
`npx ng update @angular/core@11 @angular/cli@11`

***manual update:***
- "@stomp/stompjs": "^6.0.0"
- "sockjs-client": "^1.5.0"
- "@schematics/angular": "~11.2.14"

// below command also updates @angular/cdk to 11<br>
`npx ng update @nebular/eva-icons@7.0.0 @nebular/theme@7.0.0`

### Update 11 to 12
`npx ng update @angular/core@12 @angular/cli@12`<br>

// below command also updates @angular/cdk to 12<br>
`npx ng update @nebular/eva-icons@8.0.0 @nebular/theme@8.0.0`<br>

After nebular@8 update, below error started to appear:<br>
DEPRECATION WARNING: Using / for division is deprecated and will be removed in Dart Sass 2.0.0.<br>
Recommendation: math.div(nb-theme(stepper-step-index-width), 2)<br>
More info and automated migrator: https://sass-lang.com/d/slash-div<br>

***Above case fixed in nebular@9.0.0 which is compatible with angular 13***<br>
***So will not update to nebular@9.0.0 now, will continue to update angular 12 and 13 first, then update to nebular@9.0.0***

### Update 12 to 13
`nvm use 14`<br>
`npx ng update @angular/core@13 @angular/cli@13`<br>
// below command updates @angular/cdk to 13<br>
`npx ng update @nebular/eva-icons@9.0.0 @nebular/theme@9.0.0`<br>

***manual update***<br>
- "@schematics/angular": "~13.3.11"


## Technologies/Design Decisions

- Backend: Spring Boot 2.2 with Kotlin 
- Frontend: Angular 9
- Database: MongoDB
- ORM: Spring Data
- WebSocket messaging protocol: Stomp
- WebSocket handler: Sock.js (with cross-browser fallbacks)
- Security: Spring Security
- Spring Controllers couple REST as well as WebSocket traffic
- Solid Design Principals.


## Build

#### Build and run client side(dev mode)

- Angular 9
- Nebular Theme 5 (https://akveo.github.io/nebular/)
- Bootstrap 4
- Serve 11 // http server
- ng2-file-upload
- sockjs-client & stompjs
- eva icons

`cd frontend`<br>
`nvm use 10`<br>
`npm i`<br>
`npx ng serve`<br>


#### Prod build
`npm install serve@11.3.0` // http server<br>
`npx ng build --prod`
`cd dist/frontend`<br>
`npx serve -s -p 4200`

## Features

- OAUTH2 with Google and Facebook. Users can also register via email.
- Multiple color themes available.
- Private Friends list with blocking unwanted users.
- Messages are persisted. Pwa is available provide desktop application features.
- Offline message support and sync when user is online.
- Easy add new friends via email. Like WhatsApp add via Phone number.
- Chat support Images, Audio, Video, Gif's, Map Location. Multiple files with drop in feature.

## Themes

### Default Theme

![Light Theme](./images/chat.png)

### Dark Theme

![Light Theme](./images/theme-2.png)

### Light Theme

![Light Theme](./images/theme-3.png)

## Screenshots

- [Signin Screen](./images/signin.png)
- [Signup Screen](./images/signin.png)
- [Oauth2 Confirmation](./images/token.png)
- [Home Screen](./images/home.png)
- [Chat](./images/chat.png)
- [Add New Friend](./images/new_friend.png)
- [Edit Profile](./images.edit-profile.png)

## Any questions

If you have any questions, feel free to ask me:

- **Mail**: <a href="mailto:deepanshut041@gmail.com">deepanshut041@gmail.com</a>  
- **Github**: [https://github.com/data-breach/MlAgents](https://github.com/deepanshut041/friends-chat)
- **Website**: [https://data-breach.github.io/MlAgents](https://deepanshut041.github.io/friends-chat)
- **Twitter**: <a href="https://twitter.com/deepanshut041">@deepanshut041</a>

Don't forget to follow me on <a href="https://twitter.com/deepanshut041">twitter</a>, <a href="https://github.com/deepanshut041">github</a> and <a href="https://medium.com/@deepanshut041">Medium</a> to be alerted of the new articles that I publish

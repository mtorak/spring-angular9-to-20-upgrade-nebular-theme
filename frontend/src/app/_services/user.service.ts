import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TokenStorageService } from './token-storage.service';
import { catchError, map } from 'rxjs/operators';
import { UserProfile } from '../_dtos/user/UserProfile';
import { Observable, throwError } from 'rxjs';

@Injectable()
export class UserService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private httpClient: HttpClient,
    private tokenStorageService: TokenStorageService) {
  }

  fetchProfile(): Observable<UserProfile> {
    return this.httpClient
      .get(`${environment.DOMAIN}/api/user/me`, this.httpOptions)
      .pipe(
        catchError((error) => {
          console.log(error);
          return throwError(error);
        }),
        map((user: UserProfile) => {
          this.tokenStorageService.saveUser(user)
          return user
        }));
  }

  getProfile(): UserProfile {
    return this.tokenStorageService.getUser()
  }

  logout(): void {
    this.tokenStorageService.signOut()
    window.location.reload();
  }
}

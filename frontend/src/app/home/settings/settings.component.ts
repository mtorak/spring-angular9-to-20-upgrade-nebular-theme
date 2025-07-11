import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.scss'],
    standalone: false
})
export class SettingsComponent implements OnInit {

  settings: string[] = ['Account', 'Preferences', 'Visibility'];
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  back(): void {
    this.router.navigateByUrl("/chat")
  }

}

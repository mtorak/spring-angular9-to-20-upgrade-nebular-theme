import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    standalone: false
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'frontend';

  private routerSubscription: Subscription;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.routerSubscription = this.router.events
      .subscribe((event: any) => {
        if (event instanceof NavigationEnd) {

          // Construct the full URL
          const fullUrl = window.location.origin + event.urlAfterRedirects;
          console.log('Navigation Ended - Full URL:', fullUrl);

          // You can still get more details from the ActivatedRouteSnapshot if needed
          let currentRoute = this.activatedRoute.root;
          while (currentRoute.firstChild) {
            currentRoute = currentRoute.firstChild;
          }

          const routePath = currentRoute.routeConfig?.path || '';
          const routeParams = currentRoute.snapshot.params;
          const queryParams = currentRoute.snapshot.queryParams;
          const fragment = currentRoute.snapshot.fragment;
          const routeData = currentRoute.snapshot.data;

        //   console.log('Activated Route Details:');
        //   console.log('  Path:', routePath);
        //   console.log('  Parameters:', routeParams);
        //   console.log('  Query Parameters:', queryParams);
        //   console.log('  Fragment:', fragment);
        //   console.log('  Data:', routeData);
        //
        }
      });
  }

  ngOnDestroy(): void {
    // Unsubscribe to prevent memory leaks
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

}

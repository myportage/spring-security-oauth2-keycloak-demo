import {Component, OnDestroy} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auto.config";
import {AppService} from "./app.service";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers:[AppService]
})
export class AppComponent implements OnDestroy{
  title = 'frontend';
  text='';
  helloSubscription: Subscription;

  constructor(private oauthService:OAuthService, private appService:AppService) {
    this.configure();
    this.helloSubscription= this.appService.hello().subscribe(response=>{
      this.text=response;
    });
  }

  private configure(){
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  ngOnDestroy(): void {
    this.helloSubscription.unsubscribe();
  }

  login(){
    this.oauthService.initCodeFlow();
  }

  logout(){
    this.oauthService.logOut();
  }

}

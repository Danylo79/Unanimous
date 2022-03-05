import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatListModule} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { HomeComponent } from './home/home.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AuthInterceptor } from './login/auth.interceptor';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginService } from './services/login.service';
import {MatFormFieldModule} from "@angular/material/form-field";
import { FooterComponent } from './footer/footer.component';
import { CommitteeComponent } from './committee/committee.component';
import { UserService } from './services/user.service';
import {MatSidenavModule} from "@angular/material/sidenav";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ToolbarComponent,
    ProfileComponent,
    LoginComponent,
    FooterComponent,
    CommitteeComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatButtonModule,
        MatGridListModule,
        MatListModule,
        MatCardModule,
        MatToolbarModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        FormsModule,
        MatSidenavModule
    ],
  providers: [
    HttpClient,
    LoginService,
    UserService,
    {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}

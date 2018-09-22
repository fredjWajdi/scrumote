import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppService} from "../app.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials = {username: '', password: ''};
  error = {};

  constructor(private appService: AppService, private http: HttpClient, private router: Router) {
  }

  login() {
    this.appService.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/');
    });
    return false;
  }

}
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
import { Credentials } from '../model/credentials';
import { Message, MessageStatus, MessageTemplate } from '../message/message.component';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    credentials = new Credentials();
    message = new Message();

    constructor(private api: AppService, private router: Router) {
    }

    login(): boolean {
        this.credentials.username = this.credentials.username.trim();
        if (this.credentials.username && this.credentials.password) {
            this.message.setLoading();
            this.api.authenticate(this.credentials)
                .subscribe(
                    authenticated => {
                        if (authenticated) {
                            this.message.clear();
                            this.router.navigateByUrl(this.api.redirectUrl);
                        } else {
                            this.message.setTemplate(MessageStatus.Failure, MessageTemplate.InvalidLogin);
                        }
                    },
                    error => {
                        if (error.status === 401) {
                             this.message.setTemplate(MessageStatus.Failure, MessageTemplate.InvalidLogin);
                        } else {
                            this.message.clear();
                            this.api.alert(error);
                        }
                    }
                );
        }
        return false;
    }

}

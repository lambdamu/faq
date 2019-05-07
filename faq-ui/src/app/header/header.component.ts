import { Component } from '@angular/core';
import { AppService } from '../app.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
    constructor(private api: AppService) {
    }

    authenticated() {
        return this.api.authenticated();
    }

    logout() {
        this.api.logout();
    }

    username(): string {
        return this.api.username();
    }

    isAdmin(): boolean {
        return this.api.isAdmin();
    }

    isCurrentLocale(locale: string): boolean {
        return this.api.appLocale === locale;
    }

}

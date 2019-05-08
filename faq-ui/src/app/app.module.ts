import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { XsrfInterceptor } from './xsrf-interceptor';
import { registerLocaleData } from '@angular/common';
import localeEn from '@angular/common/locales/en';
import { AppService } from './app.service';
import { AppComponent } from './app.component';
import { SearchComponent } from './search/search.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PagingComponent } from './paging/paging.component';
import { PageDescriptionComponent } from './page-description/page-description.component';
import { AlertsComponent } from './alerts/alerts.component';
import { MessageComponent } from './message/message.component';
import { FaqEditorComponent } from './faq-editor/faq-editor.component';
import { ModalComponent } from './modal/modal.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';

registerLocaleData(localeEn, 'en');

@NgModule({
    declarations: [
        AppComponent,
        SearchComponent,
        LoginComponent,
        HeaderComponent,
        FooterComponent,
        PagingComponent,
        PageDescriptionComponent,
        AlertsComponent,
        FaqEditorComponent,
        MessageComponent,
        ModalComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        ModalModule.forRoot(),
        BsDropdownModule.forRoot(),
        TypeaheadModule.forRoot()
    ],
    providers: [AppService, { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true }],
    bootstrap: [AppComponent]
})

export class AppModule { }


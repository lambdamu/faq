import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpXsrfTokenExtractor
} from '@angular/common/http';

/**
 * Custom CSRF interceptor because Angular implementation is not setting the
 * header systematically.
 */
@Injectable()
export class XsrfInterceptor implements HttpInterceptor {
    constructor(private tokenService: HttpXsrfTokenExtractor) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        let headers = req.headers.set('X-Requested-With', 'XMLHttpRequest');
        const token = this.tokenService.getToken() as string;
        if (token !== null) {
            headers = headers.set('X-XSRF-TOKEN', token);
        }
        const fw = req.clone({ headers });
        return next.handle(fw);
    }
}

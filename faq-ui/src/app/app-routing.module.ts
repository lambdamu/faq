import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { LoginComponent } from './login/login.component';
import { FaqEditorComponent } from './faq-editor/faq-editor.component';
import { AdminGuard } from './guards/admin.guard';
import { CanDeactivateGuard } from './guards/can-deactivate.guard';


const routes: Routes = [
    { path: '', redirectTo: 'search', pathMatch: 'full' },
    { path: 'search', component: SearchComponent },
    { path: 'search/:query', component: SearchComponent },
    { path: 'login', component: LoginComponent },
    { path: 'edit/:id',  canActivate: [AdminGuard], canDeactivate: [CanDeactivateGuard], component: FaqEditorComponent },
    { path: 'create',  canActivate: [AdminGuard], canDeactivate: [CanDeactivateGuard], component: FaqEditorComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    providers: [AdminGuard],
    exports: [RouterModule]
})

export class AppRoutingModule { }

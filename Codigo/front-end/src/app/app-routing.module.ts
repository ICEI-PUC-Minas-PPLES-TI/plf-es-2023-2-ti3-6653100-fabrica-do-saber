import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/pages/home/home.component';
import {LoginComponent} from './components/pages/login/login.component';
import {StudentCreateComponent} from './components/pages/student-create/student-create.component';
import {StudentEditComponent} from './components/pages/student-edit/student-edit.component';
import {StudentListComponent} from './components/pages/student-list/student-list.component';
import {UserCreateComponent} from './components/pages/user-create/user-create.component';
import {TeacherCreateComponent} from './components/pages/teacher-create/teacher-create.component';
import {TeacherEditComponent} from './components/pages/teacher-edit/teacher-edit.component';
import {TeacherListComponent} from './components/pages/teacher-list/teacher-list.component';
import {TeamCreateComponent} from './components/pages/team-create/team-create.component';
import {TeamEditComponent} from './components/pages/team-edit/team-edit.component';
import {TeamListComponent} from './components/pages/team-list/team-list.component';
import {authGuard} from './services/auth/auth.guard';
import {UserPageComponent} from './components/pages/user-page/user-page.component';
import {ReportsComponent} from './components/pages/reports/reports.component';
import {TransactionListComponent} from './components/pages/transaction-list/transaction-list.component';

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'user-page', component: UserPageComponent},
  {path: 'student-create', component: StudentCreateComponent, canActivate: [authGuard]},
  {path: 'student-edit/:id', component: StudentEditComponent, canActivate: [authGuard]},
  {path: 'student-list', component: StudentListComponent, canActivate: [authGuard]},
  {path: 'user-create', component: UserCreateComponent, canActivate: [authGuard]},
  {path: 'teacher-create', component: TeacherCreateComponent, canActivate: [authGuard]},
  {path: 'teacher-edit/:id', component: TeacherEditComponent, canActivate: [authGuard]},
  {path: 'teacher-list', component: TeacherListComponent, canActivate: [authGuard]},
  {path: 'team-create', component: TeamCreateComponent, canActivate: [authGuard]},
  {path: 'team-edit/:id', component: TeamEditComponent, canActivate: [authGuard]},
  {path: 'team-list', component: TeamListComponent, canActivate: [authGuard]},
  {path: 'reports', component: ReportsComponent, canActivate: [authGuard]},
  {path: 'transaction-list', component: TransactionListComponent, canActivate: [authGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

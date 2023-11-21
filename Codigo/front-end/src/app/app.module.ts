import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';

import { FooterComponent } from './components/layout/footer/footer.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { HomeComponent } from './components/pages/home/home.component';
import { LoginComponent } from './components/pages/login/login.component';
import { StudentCreateComponent } from './components/pages/student-create/student-create.component';
import { StudentEditComponent } from './components/pages/student-edit/student-edit.component';
import { StudentListComponent } from './components/pages/student-list/student-list.component';
import { AgePipe } from './pipes/age.pipe';
import { StudentFormComponent } from './components/forms/student-form/student-form.component';
import { ParentInfoComponent } from './components/forms/components/parent-info/parent-info.component';
import { StateSelectComponent } from './components/forms/components/state-select/state-select.component';
import { BtnCustomComponent } from './components/forms/components/btn-custom/btn-custom.component';
import { RaceSelectComponent } from './components/forms/components/race-select/race-select.component';
import { ReligionSelectComponent } from './components/forms/components/religion-select/religion-select.component';
import { TeacherCreateComponent } from './components/pages/teacher-create/teacher-create.component';
import { TeacherListComponent } from './components/pages/teacher-list/teacher-list.component';
import { TeacherEditComponent } from './components/pages/teacher-edit/teacher-edit.component';
import { TeacherFormComponent } from './components/forms/teacher-form/teacher-form.component';
import { UserCreateComponent } from './components/pages/user-create/user-create.component';
import { ZipCodeFormatDirective } from './directives/zip-code-format.directive';
import { CpfFormatDirective } from './directives/cpf-format.directive';
import { DateFormatDirective } from './directives/date-format.directive';
import { RgFormatDirective } from './directives/rg-format.directive';
import { PhoneNumberFormatDirective } from './directives/phone-number-format.directive';
import { TeamCreateComponent } from './components/pages/team-create/team-create.component';
import { TeamEditComponent } from './components/pages/team-edit/team-edit.component';
import { TeamListComponent } from './components/pages/team-list/team-list.component';
import { TeamFormComponent } from './components/forms/team-form/team-form.component';
import {
  RelationshipSelectComponent
} from './components/forms/components/relationship-select/relationship-select.component';
import { StudentSelectComponent } from './components/forms/components/student-select/student-select.component';
import { TeamSelectComponent } from './components/forms/components/team-select/team-select.component';
import { TeacherSelectComponent } from './components/forms/components/teacher-select/teacher-select.component';
import { GradeSelectComponent } from './components/forms/components/grade-select/grade-select.component';
import { CurrencyFormatDirective } from './directives/currency-format.directive';
import { AuthInterceptor } from './services/auth/auth-interceptor';
import { UserPageComponent } from './components/pages/user-page/user-page.component';
import { ReportsComponent } from './components/pages/reports/reports.component';
import { TransactionListComponent } from './components/pages/transaction-list/transaction-list.component';
import { TransactionFormComponent } from './components/forms/transaction-form/transaction-form.component';
import { CategorySelectComponent } from './components/forms/components/category-select/category-select.component';
import { TransactionCreateComponent } from './components/pages/transaction-create/transaction-create.component';
import { TransactionEditComponent } from './components/pages/transaction-edit/transaction-edit.component';
import { VacationTeamListComponent } from './components/pages/vacation-team-list/vacation-team-list.component';
import { VacationTeamCreateComponent } from './components/pages/vacation-team-create/vacation-team-create.component';
import { VacationTeamEditComponent } from './components/pages/vacation-team-edit/vacation-team-edit.component';
import { VacationTeamFormComponent } from './components/forms/vacation-team-form/vacation-team-form.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { MultiselectComponent } from './components/forms/components/multiselect/multiselect.component';
import { TransactionChartsComponent } from './components/pages/transaction-charts/transaction-charts.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    FooterComponent,
    LoginComponent,
    StudentCreateComponent,
    StudentEditComponent,
    StudentListComponent,
    AgePipe,
    StudentFormComponent,
    ParentInfoComponent,
    StateSelectComponent,
    BtnCustomComponent,
    RaceSelectComponent,
    ReligionSelectComponent,
    TeacherCreateComponent,
    TeacherListComponent,
    TeacherEditComponent,
    TeacherFormComponent,
    UserCreateComponent,
    ZipCodeFormatDirective,
    ZipCodeFormatDirective,
    CpfFormatDirective,
    DateFormatDirective,
    RgFormatDirective,
    PhoneNumberFormatDirective,
    TeamCreateComponent,
    TeamEditComponent,
    TeamListComponent,
    TeamFormComponent,
    RelationshipSelectComponent,
    StudentSelectComponent,
    TeamSelectComponent,
    TeacherSelectComponent,
    GradeSelectComponent,
    CurrencyFormatDirective,
    UserPageComponent,
    ReportsComponent,
    TransactionListComponent,
    TransactionFormComponent,
    CategorySelectComponent,
    TransactionCreateComponent,
    TransactionEditComponent,
    VacationTeamListComponent,
    VacationTeamCreateComponent,
    VacationTeamEditComponent,
    VacationTeamFormComponent,
    MultiselectComponent,
    TransactionChartsComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    ToastrModule.forRoot({
      positionClass: 'toast-center',
    }),
    HttpClientModule,
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

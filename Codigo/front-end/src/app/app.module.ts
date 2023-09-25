import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from "@angular/forms";
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
import { GuardianInfoComponent } from './components/forms/components/guardian-info/guardian-info.component';
import { StateSelectComponent } from './components/forms/components/state-select/state-select.component';
import { CustomButtonComponent } from './components/forms/components/custom-button/custom-button.component';
import { RaceSelectComponent } from './components/forms/components/race-select/race-select.component';
import { ReligionSelectComponent } from './components/forms/components/religion-select/religion-select.component';

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
    GuardianInfoComponent,
    StateSelectComponent,
    CustomButtonComponent,
    RaceSelectComponent,
    ReligionSelectComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    ToastrModule.forRoot(),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

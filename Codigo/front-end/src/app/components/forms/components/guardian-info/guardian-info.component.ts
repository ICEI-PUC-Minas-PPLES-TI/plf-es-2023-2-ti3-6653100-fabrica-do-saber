import {Component, ElementRef, Input, ViewChild} from '@angular/core';

@Component({
  selector: 'app-guardian-info',
  templateUrl: './guardian-info.component.html',
  styleUrls: ['./guardian-info.component.css']
})
export class GuardianInfoComponent {

  @Input() title!: string;
  @Input() fullName!: string;
  @Input() cpf!: string;
  @Input() rg!: string;
  @Input() email!: string;
  @Input() occupation!: string;
  @Input() company!: string;
  @Input() phoneNumber!: string;
  @Input() streetAddress!: string;
  @Input() addressNumber!: string;
  @Input() neighborhood!: string;
  @Input() cityOfResidence!: string;
  @Input() zipCode!: string;
  @Input() showCheckbox!: boolean;

  // Address Checkbox
  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;
  showAddressFields = true;

  toggleAddressFields() {
    this.showAddressFields = !this.showAddressCheckbox.nativeElement.checked;
  }

}

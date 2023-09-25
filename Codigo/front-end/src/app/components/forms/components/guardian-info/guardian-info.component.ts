import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {Guardian} from "../../../../interfaces/Guardian";

@Component({
  selector: 'app-guardian-info',
  templateUrl: './guardian-info.component.html',
  styleUrls: ['./guardian-info.component.css']
})
export class GuardianInfoComponent {

  @Input() guardian!: Guardian;
  @Input() title!: string;
  @Input() showCheckbox!: boolean;
  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

}

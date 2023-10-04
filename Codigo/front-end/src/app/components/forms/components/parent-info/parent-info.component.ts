import {Component, Input} from '@angular/core';
import {Parent} from '../../../../interfaces/Parent';

@Component({
  selector: 'app-parent-info',
  templateUrl: './parent-info.component.html',
  styleUrls: ['./parent-info.component.css']
})
export class ParentInfoComponent {

  @Input() parent!: Parent;
  @Input() title!: string;

  onHomeStateChange(newState: string): void {
    this.parent.homeState = this.formatSelect(newState);
  }

  onRelationshipStateChange(newRelationship: string): void {
    this.parent.relationship = this.formatSelect(newRelationship);
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }

}

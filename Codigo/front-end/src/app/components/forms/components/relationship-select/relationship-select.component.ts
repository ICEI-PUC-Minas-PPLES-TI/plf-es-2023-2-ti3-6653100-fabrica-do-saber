import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-relationship-select',
  templateUrl: './relationship-select.component.html',
  styleUrls: ['./relationship-select.component.css']
})
export class RelationshipSelectComponent {

  @Input() relationship!: string;
  @Output() relationshipStateChange: EventEmitter<string> = new EventEmitter<string>();

  relationships: SelectValue[] = [
    {name: 'Pai', value: 'PAI'},
    {name: 'Mãe', value: 'MAE'},
  ]

  onRelationShipChange(event: any): void {
    this.relationship = event.target.value;
    this.relationshipStateChange.emit(this.relationship);
  }
}

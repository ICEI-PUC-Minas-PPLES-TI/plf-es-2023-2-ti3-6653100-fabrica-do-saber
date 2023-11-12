import { Component, Input } from '@angular/core';
import { Transaction } from '../../../interfaces/Transaction';

@Component({
  selector: 'app-transaction-form',
  templateUrl: './transaction-form.component.html',
  styleUrls: ['./transaction-form.component.css']
})
export class TransactionFormComponent {

  @Input() transaction!: Transaction;
  @Input() title!: string;

  onCategoryChange(newCategory: string): void {
    this.transaction.category = this.formatSelect(newCategory);
  }

  formatSelect(select: any): any {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}

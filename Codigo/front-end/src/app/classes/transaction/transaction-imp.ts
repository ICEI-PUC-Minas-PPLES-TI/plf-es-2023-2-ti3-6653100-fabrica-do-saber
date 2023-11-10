import {Transaction} from '../../interfaces/Transaction';

export class TransactionImp implements Transaction {

  id: number;
  description: string;
  date: string;
  financialFlowType: string;
  category: string;
  value: number;

  constructor() {
    this.id = 0;
    this.description = '';
    this.date = '';
    this.financialFlowType = '';
    this.category = '';
    this.value = 0;
  }

}

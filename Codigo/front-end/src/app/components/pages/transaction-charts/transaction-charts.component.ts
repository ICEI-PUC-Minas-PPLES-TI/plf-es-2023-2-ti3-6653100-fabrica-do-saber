import {Component, ElementRef, ViewChild} from '@angular/core';
import {TransactionService} from '../../../services/transaction/transaction.service';
import {Transaction} from '../../../interfaces/Transaction';
import {Chart, ChartConfiguration, registerables} from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-transaction-charts',
  templateUrl: './transaction-charts.component.html',
  styleUrls: ['./transaction-charts.component.css']
})
export class TransactionChartsComponent {

  originalTransactions!: Transaction[];
  transactions!: Transaction[];
  financialFlowTypes: string[] = ['Entrada', 'Saída'];
  income!: number;
  outcome!: number;
  totalBalance !: number;
  months: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
  categories: string[] = ['Pagamento aos funcionários', 'Despesas em infraestrutura', 'Marketing institucional', 'Projetos educacionais', 'Custos administrativos', 'Eventos escolares', 'Serviços de manutenção', 'Material escolar'];

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
  }

  @ViewChild('TransactionChart') transactionChartRef!: ElementRef;

  private generateTransactionChart(): void {
    const ctx = this.transactionChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.months,
      datasets: [
        {
          label: 'Receitas',
          data: this.getDataArray(this.financialFlowTypes[0]),
          borderColor: 'green',
          backgroundColor: 'green',
        },
        {
          label: 'Despesas',
          data: this.getDataArray(this.financialFlowTypes[1]),
          borderColor: 'red',
          backgroundColor: 'red',
        }
      ]
    };
    new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
    } as ChartConfiguration);
  }

  @ViewChild('CategoryChart') categoryChartRef!: ElementRef;

  private generateCategoryChart(): void {
    const ctx = this.categoryChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.categories,
      datasets: [
        {
          label: 'Pagamento aos funcionários',
          data: this.getArrayDataByCategory('Pagamento aos funcionários'),
          borderColor: 'green',
          backgroundColor: 'green',
        },
        {
          label: 'Despesas em infraestrutura',
          data: this.getArrayDataByCategory('Despesas em infraestrutura'),
          borderColor: 'red',
          backgroundColor: 'red',
        },
        {
          label: 'Marketing institucional',
          data: this.getArrayDataByCategory('Marketing institucional'),
          borderColor: 'blue',
          backgroundColor: 'blue',
        },
        {
          label: 'Projetos educacionais',
          data: this.getArrayDataByCategory('Projetos educacionais'),
          borderColor: 'yellow',
          backgroundColor: 'yellow',
        },
        {
          label: 'Custos administrativos',
          data: this.getArrayDataByCategory('Custos administrativos'),
          borderColor: 'orange',
          backgroundColor: 'orange',
        },
        {
          label: 'Eventos escolares',
          data: this.getArrayDataByCategory('Eventos escolares'),
          borderColor: 'purple',
          backgroundColor: 'purple',
        },
        {
          label: 'Serviços de manutenção',
          data: this.getArrayDataByCategory('Serviços de manutenção'),
          borderColor: 'bronw',
          backgroundColor: 'bronw',
        },
        {
          label: 'Material escolar',
          data: this.getArrayDataByCategory('Material escolar'),
          borderColor: 'grey',
          backgroundColor: 'grey',
        }
      ]
    };
    new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
    } as ChartConfiguration);
  }

  getArrayDataByCategory(category: string): number[] {
    let total: number[] = Array.from({length: 8}, (): number => 0);
    this.originalTransactions
      .filter((transaction: Transaction): boolean => {
        const transactionCategory: string = transaction.category;
        return transactionCategory === category;
      })
      .forEach((transaction: Transaction): void => {
        total[this.categories.indexOf(transaction.category)] += transaction.value;
      });
    return total;
  }

  getDataArray(financialFlowType: string): number[] {
    const monthlyTotals: number[] = Array.from({length: 12}, (): number => 0);
    this.originalTransactions
      .filter((transaction: Transaction): boolean => {
        const transactionYear: number = new Date(this.formatDate(transaction.date)).getFullYear();
        const now: number = new Date().getFullYear();
        return (
          transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase() &&
          (now - transactionYear) <= 1
        );
      })
      .forEach((transaction: Transaction): void => {
        const transactionMonth: number = new Date(this.formatDate(transaction.date)).getMonth();
        monthlyTotals[transactionMonth] += Math.abs(transaction.value);
      });
    return monthlyTotals;
  }

  formatDate(dateString: string): string {
    const part = dateString.split('/');
    return `${part[2]}-${part[1]}-${part[0]}`;
  }

  getTransactions(): void {
    this.transactionService.getTransactions().subscribe((transactions: Transaction[]): void => {
      this.originalTransactions = transactions;
      this.transactions = [...this.originalTransactions];
      this.income = this.getFinancialFlowTypeTotal(this.financialFlowTypes[0]);
      this.outcome = this.getFinancialFlowTypeTotal(this.financialFlowTypes[1]);
      this.generateTransactionChart();
      this.generateCategoryChart();
    });
  }

  calculateTotalBalance(): void {
    this.transactionService.getTotal().subscribe((total: number): void => {
      this.totalBalance = total;
    });
  }

  getFinancialFlowTypeTotal(financialFlowType: string): number {
    return this.originalTransactions
      .filter((transaction: Transaction): boolean => transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase())
      .map((transaction: Transaction) => transaction.value)
      .reduce((total: number, value: number) => total + value, 0);
  }
}

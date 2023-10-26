import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appCurrencyFormat]'
})
export class CurrencyFormatDirective {

  constructor(private el: ElementRef) {
  }

  @HostListener('input', ['$event'])
  onInput(event: Event): void {
    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    let value: string = inputElement.value.replace(/\D/g, '');
    value = (Number(value) / 100).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    inputElement.value = value;
  }
}

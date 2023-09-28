import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appPhoneNumberFormatter]'
})
export class PhoneNumberFormatDirective {

  constructor(private el: ElementRef) { }

  @HostListener('input', ['$event']) onInput(event: InputEvent): void {

    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    const value: string = inputElement.value.replace(/\D/g, '');

    inputElement.value = this.formatPhoneNumber(value);
  }

  private formatPhoneNumber(value: string): string {

    if (value.length <= 2) {
      return value;
    } else if (value.length <= 7) {
      return `${value.slice(0, 2)} ${value.slice(2)}`;
    } else {
      return `${value.slice(0, 2)} ${value.slice(2, 7)}-${value.slice(7, 11)}`;
    }
  }

}

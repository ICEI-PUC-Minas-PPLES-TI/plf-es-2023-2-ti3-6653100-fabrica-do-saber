import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appZipCodeFormatter]'
})
export class ZipCodeFormatDirective {

  zipCodeLength: number = 8;

  constructor(private el: ElementRef) {
  }

  @HostListener('input', ['$event'])
  onInput(event: InputEvent): void {

    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    let value: string = inputElement.value.replace(/\D/g, '');

    inputElement.value = this.formatZipCode(value);
  }

  private formatZipCode(value: string): string {

    if (value.length <= 5)
      return value;
    else
      return `${value.slice(0, 5)}-${value.slice(5, 8)}`;
  }

}

import {Parent} from '../../interfaces/Parent';

export class ParentsImp implements Parent {

  fullName: string;
  cpf: string;
  rg: string;
  email: string;
  occupation: string;
  company: string;
  phoneNumber: string;
  streetAddress: string;
  addressNumber: string;
  neighborhood: string;
  cityOfResidence: string;
  zipCode: string;
  homeState: string;
  birthDate: string;
  relationship: string;

  constructor() {
    this.fullName = '';
    this.cpf = '';
    this.rg = '';
    this.email = '';
    this.occupation = '';
    this.company = '';
    this.phoneNumber = '';
    this.streetAddress = '';
    this.addressNumber = '';
    this.neighborhood = '';
    this.cityOfResidence = '';
    this.zipCode = '';
    this.homeState = '';
    this.birthDate = '';
    this.relationship = '';
  }
}

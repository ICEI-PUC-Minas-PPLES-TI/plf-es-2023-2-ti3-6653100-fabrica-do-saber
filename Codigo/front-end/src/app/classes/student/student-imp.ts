import { Student } from '../../interfaces/Student';
import { Parent } from '../../interfaces/Parent';
import { ParentsImp } from '../parent/parents-imp';

export class StudentImp implements Student {

  parents: Parent[] = [];
  teamIds: number[];
  id: number;
  fullName: string;
  registrationDate: string;
  birthDate: string;
  hometown: string;
  homeState: string;
  nationality: string;
  religion: string;
  race: string;
  streetAddress: string;
  addressNumber: string;
  addressComplement: string;
  neighborhood: string;
  cityOfResidence: string;
  zipCode: string;

  constructor() {
    this.parents = [new ParentsImp(), new ParentsImp()];
    this.teamIds = [];
    this.id = 0;
    this.fullName = '';
    this.registrationDate = '';
    this.birthDate = '';
    this.hometown = '';
    this.homeState = '';
    this.nationality = '';
    this.religion = '';
    this.race = '';
    this.streetAddress = '';
    this.addressNumber = '';
    this.addressComplement = '';
    this.neighborhood = '';
    this.cityOfResidence = '';
    this.zipCode = '';
  }
}

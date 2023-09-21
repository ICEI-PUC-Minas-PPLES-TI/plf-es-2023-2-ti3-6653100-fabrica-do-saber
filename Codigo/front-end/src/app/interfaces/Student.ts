export interface Student {
  id: number;
  responsibles: {
    id: number;
    name: string;
    cpf: string;
    rg: string;
    email: string;
    occupation: string;
    phoneNumber: string;
    company: string;
    streetAdress: string;
    number: string;
    neighbourhood: string;
    cityResidence: string;
    postalCode: string;
  }[];
  fullName: string;
  yearRegistration: string;
  grade: string;
  education: string;
  dateOfBirth: string;
  cityBirth: string;
  state: string;
  nationality: string;
  religion: string;
  race: string;
}

CREATE TABLE IF NOT EXISTS Patients (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY ,
  Name VARCHAR(255) not null ,
  Surname VARCHAR(255) not null ,
  Patronymic VARCHAR(255) ,
  Phone_Number VARCHAR(11)
);

CREATE TABLE IF NOT EXISTS Doctors (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY ,
  Name VARCHAR(255) not null ,
  Surname VARCHAR(255) not null ,
  Patronymic VARCHAR(255) ,
  Specialty VARCHAR(255) not null
);

CREATE TABLE IF NOT EXISTS Prescriptions (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY ,
  Description VARCHAR(8000) not null ,
  Patient_id BIGINT FOREIGN KEY REFERENCES Patients(ID) ,
  Doctor_id BIGINT FOREIGN KEY REFERENCES Doctors(ID) ,
  Creation_date DATE ,
  Days_validity INTEGER CHECK (Days_validity > 0),
  Priority VARCHAR(20) CHECK (Priority IN ('Normal', 'Cito', 'Statim'))
)
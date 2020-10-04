import React, { useState, useEffect } from 'react';
import { Translate, translate } from 'react-jhipster';
import { connect } from 'react-redux';
import PasswordStrengthBar from '../../../shared/layout/password/password-strength-bar';
import { IRootState } from '../../../shared/reducers';
import { handleRegister, reset, verifyAdresse } from './register.reducer';
import {UserInfoForm,AdressForm,SuccessPage} from './register-components'

export interface IRegisterProps extends StateProps, DispatchProps {}

export const RegisterPage = (props: IRegisterProps) => {
  const [userData, setUserData] = useState({});
  const[step, setStep] = useState(1);
  const[errorCount, setErrorCount] = useState(0);
  const nextStep= (data) => {
    if(errorCount===0){
      setUserData({...userData,
        ...data})
       setStep(step+1)
    }
  }
  const handleValidSubmit =(values) => {
     props.handleRegister(values, props.currentLocale);
  };
 
  const prevStep=()=>{
    setStep(step-1)
  }
  const handleChange = input => e => {
    this.setState({ [input]: e.target.value });
  };
  
  switch (step) {
    case 1:
    return (
        <UserInfoForm
        nextstep={nextStep}
        errorcount={errorCount}
        verifyadresse={props.verifyAdresse}
        loadingmail={props.loadingMail}
        mailexist={props.mailExist}
        seterrorcount={setErrorCount}
        handlechange={handleChange}
      />
    
  );
  case 2:
    return (
        <AdressForm
        nextstep={nextStep}
        handlechange={handleChange}
       
        prevstep={prevStep}
      />
  );
  case 3:
    return(
      <SuccessPage
        handlevalidsubmit={handleValidSubmit}
        userData={userData}
      />
    );
    

  default:
    return(<div> test</div>);
}
};
const mapStateToProps = ({ locale, register}: IRootState) => ({
  currentLocale: locale.currentLocale,
  loadingMail:register.loadingMail,
  mailExist:register.mailExist
});

const mapDispatchToProps = { handleRegister, reset,verifyAdresse };
type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage);

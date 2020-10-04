import React from 'react';
function CheckoutSteps(props) {
  return <div className="checkout-steps">
    <div className={props.step1 ? 'active' : ''} >Informations personelles</div>
    <div className={props.step2 ? 'active' : ''} >Adresse</div>
    <div className={props.step3 ? 'active' : ''} >Envoie Mail</div>
  </div>
}
 
export default CheckoutSteps;
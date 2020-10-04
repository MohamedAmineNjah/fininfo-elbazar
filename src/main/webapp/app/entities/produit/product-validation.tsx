import React, { useState } from 'react';
import { TextField, Snackbar, Button } from '@material-ui/core';
import MuiAlert, { AlertProps } from '@material-ui/lab/Alert';
import Slide, { SlideProps } from '@material-ui/core/Slide';

const handlePrixTVAChange = (name, value, valeurs, setvaleurs) => {
  const tauxtva = value ? value : 0;
  const prixTTC = valeurs['prixTTC'] ? valeurs['prixTTC'] : 0;
  const dynamicprixht = parseInt(prixTTC.toString(), 10) / (1 + parseInt(tauxtva.toString(), 10) / 100);
  setvaleurs({ ...valeurs, [name]: value, ['prixHT']: dynamicprixht.toFixed(3) });
};

const handlePrixTTCChange = (name, value, valeurs, setvaleurs) => {
  const prixttc = value ? value : 0;
  const tauxTVA = valeurs['tauxTVA'] ? valeurs['tauxTVA'] : 0;
  const dynamicprixht = parseInt(prixttc.toString(), 10) / (1 + parseInt(tauxTVA.toString(), 10) / 100);
  setvaleurs({ ...valeurs, [name]: value, ['prixHT']: dynamicprixht.toFixed(3) });
};

export const TextValidator = (props: any) => {
  const [myHelperErrorText, setmyHelperErrorText] = useState('');
  const [isFieldError, setisFieldError] = useState(false);

  const getValidationMessage = regexType => {
    switch (regexType) {
      case 'alphabetique':
        return 'Ce champ est de type alphabétique';

      case 'numerique':
        return 'Ce champ est de type numérique';

      case 'refregex':
        return "Ce champ a une longueur max de 25 et ne contient pas d'espace";

      case 'alphanumerique':
        return 'Ce champ est de type alphanumérique';

      case 'FloatRegex':
        return 'Ce champ est de type Décimal';

      default:
        break;
    }
  };

  const handleValidateInputChanges = (
    event,
    values,
    initialvalue,
    setValuesFunction,
    patternRegex,
    isValidateErrorSetter,
    regexValues,
    globalErrorSetter
  ) => {
    const { name, value } = event.currentTarget;
    if (!initialvalue) {
      values[name] = '';
    }
    if (event.target.value.match(patternRegex.pattern)) {
      setValuesFunction({ ...values, [name]: value });
      if (name === 'tauxTVA') {
        handlePrixTVAChange(name, value, values, setValuesFunction);
      }
      if (name === 'prixTTC') {
        handlePrixTTCChange(name, value, values, setValuesFunction);
      }

      setmyHelperErrorText('');
      isValidateErrorSetter(false);
      setisFieldError(false);
      globalErrorSetter({ ...regexValues, [name]: { ['isOnError']: false } });
    } else {
      setmyHelperErrorText(getValidationMessage(patternRegex.type));
      setValuesFunction({ ...values, [name]: value });
      isValidateErrorSetter(true);
      setisFieldError(true);
      globalErrorSetter({ ...regexValues, [name]: { ['isOnError']: true } });
    }
  };

  const handleVaue = (data, field): any => {
    if (data) {
      return data[field];
    } else {
      if (field === 'tauxTVA' || field === 'prixTTC') {
        return 0;
      } else {
        return '';
      }
    }
  };

  return (
    <TextField
      {...props}
      helperText={myHelperErrorText}
      error={isFieldError}
      value={handleVaue(props.values.data, props.values.field)}
      onChange={e =>
        handleValidateInputChanges(
          e,
          props.values.data,
          props.values.data[props.values.field],
          props.values.setter,
          props.regx,
          props.values.errorsetter,
          props.values.regValue,
          props.values.globalErrorSetter
        )
      }
    />
  );
};

function Alert(props: AlertProps) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

export const SnackBarCustom = (props: any) => {
  return (
    <div>
      <Snackbar
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        open={props.isSnackOpen}
        autoHideDuration={6000}
        onClose={props.handleClose}
        action={
          <React.Fragment>
            <Button color="secondary" size="small" onClick={props.handleClose}>
              UNDO
            </Button>
          </React.Fragment>
        }
      >
        <Alert severity="error">{props.textMessage}</Alert>
      </Snackbar>
    </div>
  );
};

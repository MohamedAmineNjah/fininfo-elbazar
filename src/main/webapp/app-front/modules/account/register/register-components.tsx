import React, { useState, useEffect } from 'react';
import { Translate, translate } from 'react-jhipster';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { Row, Nav, Alert } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Box from '@material-ui/core/Box';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormLabel from '@material-ui/core/FormLabel';
import TextField from '@material-ui/core/TextField';
import { green } from '@material-ui/core/colors';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import PasswordStrengthBar from '../../../shared/layout/password/password-strength-bar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import CheckoutSteps from './checkoutSteps';
import register from './register';
import { FormControl, InputLabel, Input, Popover } from '@material-ui/core/';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
import { Gouvernorats } from '../../../shared/json/gouvernorat.json';
import { Villes } from '../../../shared/json/villes.json';
import { Localites } from '../../../shared/json/localite.json';
import appConfig from '../../../config/constants';
import { __values } from 'tslib';
import PopupState, { bindTrigger, bindPopover } from 'material-ui-popup-state';
import ReCAPTCHA from "react-google-recaptcha";
import CGUModal from '../../CGU/CGU_modal';

export const TextValid = (props: any) => {
  const [errorText, setErrorText] = useState('');
  const [error, setError] = useState(false);
  const [iserror, setIsError] = useState(false);
  const textRegex = new RegExp(props.patter);
  const [Value,setValue] = useState();
 
    const checkValid=val =>{
      if(val){
        if ( val.match(textRegex)) {
          setErrorText('');
          if(props.errorcount && props.errorcount>0 && iserror===true){
            props.seterrorcount(props.errorcount-1);
            setIsError(false)
          }
          setError(false);
         
        } else {
            if(!iserror){
              props.seterrorcount(props.errorcount+1);
              setIsError(true)
            }
          setError(true);
          setErrorText(props.errortext);
        }
      }
      
    }
  const onChange = event => {
    setValue(event.target.value)
    checkValid(event.target.value);
    if (event.target.name === 'password') {
      props.testpassword(event.target.value);
    }
    if (props.change) {
      props.change(event.target.value);
    }
  };
  useEffect(()=>{
    if(props.mailexist=== true){

      if(!iserror){
        props.seterrorcount(props.errorcount+1);
        setIsError(true)
      }
    setError(true);
    setErrorText("Cet Email est utilisé");
    }else{
      setErrorText('');
      if(props.errorcount && props.errorcount>0 && iserror===true){
        props.seterrorcount(props.errorcount-1);
        setIsError(false)
      }
      setError(false);
      checkValid(Value);
    }
  },[props.mailexist])
  return <TextField
      autoComplete={props.autoComplete}
      name={props.name}
      type={props.type} 
      required={props.required}
      fullWidth
      onBlur={props.onBlur}
      id={props.id}
      value={props.value}
      label={props.label}
      autoFocus
      helperText={errorText}
      onChange={onChange}
      defaultValue={props.defaultValue}
      error={error}
    />;
};
const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  root: {
    flexGrow: 1,
  },
  recapItem: {
    'margin-left': '25%',
  },
  recapTitle: {
    float: 'left',
  },
  avatar: {
    margin: theme.spacing(2),
    backgroundColor: theme.palette.secondary.main,
  },
  date: {
    width: '100%', // Fix IE 11 issue.
  },
  typography: {
    padding: theme.spacing(2),
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    'background-color': green[400],
    '&:hover': {
      'background-color': green[500],
    },
    margin: theme.spacing(0, 0, 2),
  },
}));

export const UserInfoForm = props => {
  const [password, setPassword] = useState('');
  const [radiociv, setradiociv] = React.useState('female');
  const [enableForm, setEnableForm] = useState(0);
  const classes = useStyles();

  const showPassword = () => {
    const x: any = document.getElementById('firstPassword');

    if (x.type === 'password') {
      x.type = 'text';
    } else {
      x.type = 'password';
    }
  };
  const handelChangeRadio = event => setradiociv(event.target.value);
  const handleNextSubmit = event => {
    event.preventDefault();
    const data = {};
    for (let i = 0; i < event.target.length - 1; i++) {
      if (event.target[i].name) {
        data[event.target[i].name] = event.target[i].value;
      }
    }
    props.nextstep(data);
  };
  useEffect(()=>{
    if(props.loadingmail===false){
      setEnableForm(0);
    }
  },[props.loadingmail])
  const validateMail = (event) =>{
    if(event.target.value!==""){
     setEnableForm(1);
     props.verifyadresse(event.target.value);
    }
    
  }
  const updatePassword = event => setPassword(event);

  return (
    <Container component="main" className={classes.root} maxWidth="md">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Bienvenue 
        </Typography>
        <CheckoutSteps step1></CheckoutSteps>
        <form className={classes.form} onSubmit={handleNextSubmit}>
          <Grid container spacing={3}>
            <Grid item xs={12} sm={8}>
              <FormControl component="fieldset">
                <FormLabel component="legend">Civilité</FormLabel>
                <RadioGroup row name="civilité" value={radiociv} onChange={handelChangeRadio}>
                  <FormControlLabel value="Mme" control={<Radio required={true} />} label="Monsieur" />
                  <FormControlLabel value="M" control={<Radio required={true} />} label="Madame" />
                </RadioGroup>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextValid
                autoComplete="fname"
                name="firstName"
                errorcount={props.errorcount}
                seterrorcount={props.seterrorcount}
                required
                fullWidth
                id="firstName"
                patter="^[a-zA-Z]+[a-zA-Z ]*$"
                errortext={translate('register.messages.validate.firstName.pattern')}
                label={translate('global.form.firstName.label')}
                autoFocus
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextValid
                required
                fullWidth
                errorcount={props.errorcount}
                seterrorcount={props.seterrorcount}
                id="lastName"
                label={translate('global.form.lastName.label')}
                name="lastName"
                autoComplete="lname"
                patter="^[a-zA-Z]+[a-zA-Z ]*$"
                errortext={translate('register.messages.validate.lastName.pattern')}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="date"
                label="Date de naissance"
                type="date"
                name="dateDeNaissance"
                className={classes.date}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextValid
                required
                fullWidth
                errorcount={props.errorcount}
                seterrorcount={props.seterrorcount}
                type="number"
                id="tel"
                label={translate('global.form.tel.label')}
                name="tel"
                autoComplete="tel"
                patter="^\d{8,8}$"
                errortext={translate('global.messages.validate.tel.invalid')}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextValid
                required
                fullWidth
                errorcount={props.errorcount}
                seterrorcount={props.seterrorcount}
                onBlur={validateMail}
                id="email"
                mailexist={props.mailexist}
                label={translate('global.form.email.label')+" (votre login)"}
                name="email"
                patter='^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$'
                errortext={translate('global.messages.validate.email.invalid')}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextValid
                required
                fullWidth
                errorcount={props.errorcount}
                seterrorcount={props.seterrorcount}
                name="password"
                label={translate('global.form.password.label')}
                type="password"
                id="firstPassword"
                patter="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$"
                testpassword={updatePassword.bind(this)}
                errortext={translate('global.messages.validate.newpassword.invalid')}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl component="fieldset" className={classes.date}>
                <FormLabel component="legend">Mode de paiement préféré *</FormLabel>
                <Select labelId="demo-simple-select-helper-label" name="reglement" defaultValue="Cash" required id="demo-simple-select-helper">
                  <MenuItem value={'CarteBancaire'}>Carte bancaire</MenuItem>
                  <MenuItem value={'Cash'}>Espéce</MenuItem>
                  <MenuItem value={'Cheque'}>Cheque</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            
            <Grid item sm={3}>
              <FormControlLabel
                control={<Checkbox value="showPassword" color="primary" onClick={showPassword} />}
                label={translate('global.messages.validate.newpassword.showPassword')}
              />
            </Grid>
            <Grid item sm={3}>
              <PasswordStrengthBar password={password} />
            </Grid>
          </Grid>

          <Grid container direction="row-reverse">
            <Grid item md={2}>
              <Button type="submit" fullWidth variant="contained" color="secondary" disabled={enableForm===1} className={classes.submit}>
                Suivant
              </Button>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
};
export const AdressForm = (props: any) => {
  const [listVilles, setListVilles] = useState([]);
  const [listlocality, setListlocality] = useState([]);
  const [villee, setVillee]= useState(true);
  const [showModal, setShowModal] = React.useState(false);
  const [localityy, setLocalityy]= useState(true);
  const [allowSubmit,setAllowSubmit]= useState(false);

  const classes = useStyles();
  const handleClose = () => {
    setShowModal(false);
  };
  const HandleShow = (event) => {
    event.stopPropagation()
    setShowModal(true);
  };
  const HandleAllowSubmit = (event) => {
    event.stopPropagation()
    setAllowSubmit(!allowSubmit);
  };

  const handelGouv = (event, gouv) => {
    setVillee(!villee);
    setLocalityy(!localityy);
    if(gouv){
      const listeVilles = Villes.filter(ville => parseInt(ville.IDGouvernorat, 10) === gouv.value);
      setListVilles(listeVilles);
    }
  
   
  };

  const handelVille = (event, ville) => {
    setLocalityy(!localityy);
    if(ville){
    const listlocalite = Localites.filter(Localite => parseInt(Localite.IDVille, 10) === parseInt(ville.ID, 10));
    setListlocality(listlocalite);
    }
  };
  const handleNextSubmit = event => {
    event.preventDefault();
    const data = {};
    if(allowSubmit){
      for (let i = 0; i < event.target.length - 1; i++) {
        if (event.target[i].name) {
          data[event.target[i].name] = event.target[i].value;
        }
      }
    
        props.nextstep(data);
    }
    
  };
  return (
    <Container component="main" maxWidth="md">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <CheckoutSteps step1 step2></CheckoutSteps>
        <form className={classes.form} onSubmit={handleNextSubmit}>
          <Grid container spacing={2}>
            <Grid item   xs={6} >
              <TextValid
                autoComplete="Avenue"
                name="avenue"
                required
                fullWidth
                id="avenue"
                label={translate('global.form.avenue.label')}
                autoFocus
              />
            </Grid>

            <Grid item   xs={6} >
              <Autocomplete
                id="Gouvernorat"
                options={Gouvernorats}
                getOptionLabel={option => option.label}
                onChange={(event, value) => handelGouv(event, value)}
                renderInput={params => <TextField required {...params} name="gouvernorat" label="Gouvernorat" />}
              />
            </Grid>
            <Grid item  xs={6}>
              <Autocomplete
                id="Ville"
                key={villee.toString()}
                options={listVilles}
                getOptionSelected={(option, value) => option.id === value.id}
                getOptionLabel={option => option.Ville}
                onChange={(event, value) => handelVille(event, value)}
                renderInput={params => <TextField required {...params} id="Villeinput" name="ville" label="Ville" />}
              />
            </Grid>
            <Grid item  xs={6}>
              <Autocomplete
                id="localite"
                key={localityy.toString()}
                options={listlocality}
                getOptionLabel={option => option.Localite}
                renderInput={params => <TextField required {...params} name="localite" label="Cité" />}
              />
            </Grid>
            <Grid item xs={6}>
              <TextValid
                autoComplete="commentaireLibre"
                name="indication"
                fullWidth
                id="commentaireLibre"
                label={translate('global.form.commentaireLibre.placeholder')}
                autoFocus
              />
            </Grid>
            <Grid item md={6}>
       
              <FormControlLabel
                control={<Checkbox value="showPassword" color="primary" />}
                label={""}
                onClick={(event)=>{HandleAllowSubmit(event)}}
              />
              <span>J&apos;accepte <a style={{color: "#e21717"}} onClick={(event)=>{HandleShow(event)}}>les conditions générales d&apos;utilisation</a> </span>
            </Grid>
          </Grid>
          <Grid container direction="row-reverse" spacing={2}>
            <Grid item md={2}>
            {!allowSubmit ? 
                    (
                    <PopupState variant="popover" popupId="demo-popup-popover">
                      {(popupState) => (
                        <div>
                         <Button type="submit" fullWidth variant="contained" color="secondary" {...bindTrigger(popupState)} className={classes.submit}>
                          {' '}
                          {'Suivant >'}
                        </Button>
                          <Popover
                            {...bindPopover(popupState)}
                            anchorOrigin={{
                              vertical: 'bottom',
                              horizontal: 'left',
                            }}
                            transformOrigin={{
                              vertical: 'top',
                              horizontal: 'right',
                            }}
                          >
                            <Box p={2}>
                              <Typography> {"Vous devez avoir lu et approuver les conditions"} </Typography>
                              <Typography> {"générales d'utilisation pour continuer"} </Typography>
                            </Box>
                          </Popover>
                        </div>
                      )}
                    </PopupState>)
                    :(<Button type="submit" fullWidth variant="contained" color="secondary" className={classes.submit}>
                    {' '}
                    {'Suivant >'}
                  </Button>)
                  }
              
            </Grid>
            <Grid item md={2}>
              <Button fullWidth variant="contained" color="secondary" className={classes.submit} onClick={props.prevstep}>
                {' '}
                {'< Retour'}{' '}
              </Button>
            </Grid>
          </Grid>
        </form>
      </div>
      <CGUModal showModal={showModal} handleClose={handleClose} />
    </Container>
  );
};
export const SuccessPage = (props: any) => {
  const classes = useStyles();
  props.handlevalidsubmit(props.userData);
  return (
    <Container component="main" maxWidth="md">
      <CssBaseline />
      <div className={classes.paper}>
        <CheckoutSteps step1 step2 step3></CheckoutSteps>
        <Row>
          <Alert className={classes.paper} color="success">
            
            <span>Votre compte client a été enregisté avec success</span>
            <br />
            <span>
              Un email de confirmation vous sera envoyé sur l&lsquo;adresse : <b>{props.userData.email}</b>
            </span>
          </Alert>
        </Row>
        <Nav id="header-tabs" navbar>
          <NavItem>
            <NavLink tag={Link} to="/" className="d-flex align-items-center">
              <Button fullWidth variant="contained" color="secondary" className={classes.submit}>
                <FontAwesomeIcon icon="home" />
                <span> Acceuil </span>
              </Button>
            </NavLink>
          </NavItem>
        </Nav>
      </div>
    </Container>
  );
};

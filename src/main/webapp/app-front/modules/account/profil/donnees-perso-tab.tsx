import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Translate, translate } from 'react-jhipster';
import { connect } from 'react-redux';
import PasswordStrengthBar from '../../../shared/layout/password/password-strength-bar';
import { IRootState } from '../../../shared/reducers';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PermContactCalendarIcon from '@material-ui/icons/PermContactCalendar';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import { getSession } from '../../../shared/reducers/authentication';
import { getEntity } from '../../../entities/client/client.reducer';
import { savePassword, reset } from '../../account/password/password.reducer';
import {
  Table,
  TableRow,
  TableCell,
  TableBody,
  Grid,
  ExpansionPanelSummary,
  ExpansionPanelDetails,
  Typography,
  ExpansionPanel,
  FormLabel,
  FormControlLabel,
  RadioGroup,
  FormControl,
  Radio,
  TextField,
  Select,
  MenuItem,
  ThemeProvider,
  Modal,
  Fade,
  Backdrop,
  TableHead,
} from '@material-ui/core';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col } from 'reactstrap';
import Button from '@material-ui/core/Button';
import { IClient } from '../../../shared/model/client.model';
import { TextValid } from '../register/register-components';
import { cleanEntity } from '../../../shared/util/entity-utils';
import CircularProgress from '@material-ui/core/CircularProgress';
import { theme, SnackBarCustom, useStyles } from './donnees-perso-tab-style';
import EmojiEmotionsIcon from '@material-ui/icons/EmojiEmotions';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import WarningIcon from '@material-ui/icons/Warning';
import { useStylesClient } from '../../../../app/shared/util/entity-style';

export interface IDonneesPersoProps extends StateProps, DispatchProps {}

export const DonneesPerso = (props: any) => {
  const classes = useStyles();
  const [password, setPassword] = useState('');
  const [client, setclient] = useState<IClient>(null);
  const [clientTemp, setclientTemp] = useState<IClient>(null);
  const [radiociv, setradiociv] = React.useState('female');
  const [datenaissance, setdatenaissance] = React.useState('');
  const [nom, setnom] = React.useState('');
  const [prenom, setprenom] = React.useState('');
  const [pointfidelite, setpointsFidelite] = React.useState(0);

  const [email, setemail] = React.useState('');
  const [mobile, setmobile] = React.useState(0);
  const [errorCount, setErrorCount] = React.useState(0);
  const [selectreglement, setselectreglement] = React.useState('');

  const [issnackopen, setissnackopen] = React.useState(false);
  const [issnackpass, setissnackpass] = React.useState(false);
  const [issnackpassincorrectopen, setissnackpassincorrectopen] = React.useState(false);

  const apiUrl = 'api/clients';
  const apiUrlPF = 'api/client/points-fid';

  useEffect(() => {
    const requestUrl = `${apiUrl}/${props.accountdetail.id}`;
    const fetchData = async () => {
      const result = await axios.get<IClient>(requestUrl);

      setclient({ ...result.data });
      setclientTemp({ ...result.data });
      setradiociv(result.data.civilite);
      setdatenaissance(result.data.dateDeNaissance);
      setnom(result.data.nom);
      setprenom(result.data.prenom);
      setemail(result.data.email);
      setmobile(result.data.mobile);
      setselectreglement(result.data.reglement);
    };

    fetchData();
  }, []);
  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get(apiUrlPF);

      setpointsFidelite(result.data);
    };
    fetchData();
  }, []);
  const apiUrl1 = 'api/account';

  const onSnackClosepass = () => {
    setissnackpassincorrectopen(false);
  };
  const onSnackClose = () => {
    setissnackopen(false);
    setissnackpass(false);
    const requestUrl = `${apiUrl}/${props.accountdetail.id}`;
    const fetchData = async () => {
      const result = await axios.get<IClient>(requestUrl);

      setclient({ ...result.data });
      setclientTemp({ ...result.data });
    };
    fetchData();
    window.scrollTo(0, 0);
    props.setrefresh();
  };

  const handleDrawerClose = () => {};

  const handleValidSubmit = event => {
    event.preventDefault();
    let currentPassword = '';
    let newPassword = '';

    for (let i = 0; i < event.target.length - 1; i++) {
      if (event.target[i].name === 'currentPassword') {
        currentPassword = event.target[i].value;
      }
      if (event.target[i].name === 'newPassword') {
        newPassword = event.target[i].value;
      }
    }
    axios
      .post(`${apiUrl1}/change-password`, { currentPassword, newPassword })
      .then(res => {
        console.log(res);
        setissnackpass(true);
      })
      .catch(error => {
        if (error.response) {
          // console.log(error.response);
          setissnackpassincorrectopen(true);
        }
      });
  };
  //   axios.post(`${apiUrl1}/change-password`, { currentPassword, newPassword }).then(() => {
  //     setissnackpass(true);
  //   });
  // };
  // if (response.status === 400) {
  //   console.log('ok');
  //   console.log(response);
  //   return setissnackpassincorrectopen(true);
  // }

  const updatePassword = event => setPassword(event.target.value);

  const handelChangeDate = event => {
    setdatenaissance(event.target.value);
  };

  const handelChangeRadio = event => {
    setradiociv(event.target.value);
  };
  const handelChangeSelect = event => {
    setselectreglement(event.target.value);
  };
  const annulerSaisie = () => {
    /* eslint-disable no-console */
    console.log(clientTemp);
    // client.dateDeNaissance = clientTemp['dateDeNaissance'];
    const requestUrl = `${apiUrl}/${props.accountdetail.id}`;
    const fetchData = async () => {
      const result = await axios.get<IClient>(requestUrl);

      setclient({ ...result.data });
      setclientTemp({ ...result.data });
      setradiociv(result.data.civilite);
      setdatenaissance(result.data.dateDeNaissance);
      setnom(result.data.nom);
      setprenom(result.data.prenom);
      setemail(result.data.email);
      setmobile(result.data.mobile);
      setselectreglement(result.data.reglement);
    };

    fetchData();
  };

  const handleNextSubmit = event => {
    event.preventDefault();
    if (errorCount === 0) {
      const data = { ...client };
      for (let i = 0; i < event.target.length - 1; i++) {
        if (event.target[i].name) {
          if (event.target[i].name === 'civilite' && !event.target[i].checked) {
            continue;
          }

          data[event.target[i].name] = event.target[i].value;
        }
      }
      axios.put(apiUrl, cleanEntity(data)).then(() => {
        setissnackopen(true);
      });
    }
  };
  const classees = useStylesClient();

  const renderPackImage = clientPack => {
    switch (clientPack) {
      case 'Silver':
        return (
          <React.Fragment>
            <div>
              <img className={classees.profilelogo} src="content/images/silver.png" />{' '}
            </div>
          </React.Fragment>
        );
      case 'Gold':
        return (
          <React.Fragment>
            <div>
              <img className={classees.profilelogo} src="content/images/golden.png" />{' '}
            </div>
          </React.Fragment>
        );
      case 'Platinium':
        return (
          <React.Fragment>
            <div>
              <img className={classees.profilelogo} src="content/images/platinium.png" />{' '}
            </div>
          </React.Fragment>
        );
      default:
        break;
    }
  };
  return (
    <React.Fragment>
      {' '}
      <Grid container item xs={12}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ textAlign: 'center' }}>
                <span className="paddingPoint">{client ? client.email : ''}</span>
                <div className="imageBadge" style={{ width: 'fit-content', margin: 'auto' }}>
                  {client ? renderPackImage(client.profile) : ''}
                </div>
                <span className="paddingPoint">
                  {' '}
                  {pointfidelite ? pointfidelite : ''}
                  {pointfidelite !== 0 ? ' Points' : ''}
                </span>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell>
                <ExpansionPanel>
                  <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1a-content" id="panel1a-header">
                    <PermContactCalendarIcon className={classes.icon} />
                    <Typography className={classes.heading} style={{ marginLeft: '.5rem' }}>
                      <h6>
                        {' '}
                        <b>Gérer mes données personelles</b>
                      </h6>
                      Gérer vos coordonnées et vos données personelles
                    </Typography>
                  </ExpansionPanelSummary>
                  <ExpansionPanelDetails>
                    {!client ? (
                      <div className={classes.root}>
                        <CircularProgress color="secondary" />
                      </div>
                    ) : (
                      <React.Fragment>
                        <form className={classes.form} onSubmit={handleNextSubmit}>
                          <Grid container spacing={3}>
                            <Grid item xs={12} sm={8}>
                              <FormControl component="fieldset">
                                <FormLabel component="legend">Civilité</FormLabel>
                                <RadioGroup row name="civilite" value={radiociv} onChange={handelChangeRadio}>
                                  <FormControlLabel value="M" control={<Radio required={true} />} label="Monsieur" />
                                  <FormControlLabel value="Mme" control={<Radio required={true} />} label="Madame" />
                                </RadioGroup>
                              </FormControl>
                            </Grid>

                            <Grid item xs={12} sm={6}>
                              <TextValid
                                required
                                fullWidth
                                errorcount={errorCount}
                                seterrorcount={setErrorCount}
                                change={setnom}
                                id="lastName"
                                value={nom}
                                name="nom"
                                defaultValue={client ? client.nom : ''}
                                label={translate('global.form.lastName.label')}
                                patter="[a-z]"
                                errortext={translate('register.messages.validate.lastName.pattern')}
                              />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                              <TextValid
                                autoComplete="fname"
                                change={setprenom}
                                errorcount={errorCount}
                                seterrorcount={setErrorCount}
                                value={prenom}
                                name="prenom"
                                defaultValue={client ? client.prenom : ''}
                                required
                                fullWidth
                                id="firstName"
                                patter="[a-z]"
                                errortext={translate('register.messages.validate.firstName.pattern')}
                                label={translate('global.form.firstName.label')}
                                autoFocus
                              />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                              {/* <TextValid
                                required
                                fullWidth
                                change={setemail}
                                errorcount={errorCount}
                                seterrorcount={setErrorCount}
                                value={email}
                                id="email"
                                label={translate('global.form.email.label')}
                                name="email"
                                defaultValue={client ? client.email : ''}
                                autoComplete="email"
                                patter='^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$'
                                errortext={translate('global.messages.validate.email.invalid')}
                                InputLabelProps={{
                                  shrink: true,
                                }}
                              /> */}
                              <TextField
                                required
                                id="date"
                                label="Date de naissance"
                                onChange={handelChangeDate}
                                type="date"
                                name="dateDeNaissance"
                                value={client ? datenaissance : ''}
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
                                errorcount={errorCount}
                                seterrorcount={setErrorCount}
                                change={setmobile}
                                value={mobile}
                                type="number"
                                id="tel"
                                label={translate('global.form.tel.label')}
                                name="mobile"
                                defaultValue={client ? client.mobile : ''}
                                patter="^\d{8,8}$"
                                errortext={translate('global.messages.validate.tel.invalid')}
                              />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                              <FormControl component="fieldset" className={classes.date}>
                                <FormLabel component="legend">Mode de paiement préféré *</FormLabel>
                                <Select
                                  labelId="demo-simple-select-helper-label"
                                  defaultValue={client ? client.reglement : ''}
                                  onChange={handelChangeSelect}
                                  value={selectreglement}
                                  name="reglement"
                                  required
                                  id="demo-simple-select-helper"
                                >
                                  <MenuItem value={'CarteBancaire'}>Carte bancaire</MenuItem>
                                  <MenuItem value={'Cash'}>Espéce</MenuItem>
                                  <MenuItem value={'Cheque'}>Cheque</MenuItem>
                                </Select>
                              </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6}></Grid>
                          </Grid>
                          <ThemeProvider theme={theme}>
                            <Button variant="contained" type="submit" onClick={handleDrawerClose}>
                              <Translate contentKey="password.form.button">Save</Translate>
                            </Button>
                          </ThemeProvider>
                          <Button variant="contained" color="secondary" className={classes.button} onClick={annulerSaisie}>
                            Annuler
                          </Button>
                        </form>
                      </React.Fragment>
                    )}
                  </ExpansionPanelDetails>
                </ExpansionPanel>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
        <Table>
          <TableBody>
            <TableRow>
              <TableCell>
                <ExpansionPanel>
                  <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1a-content" id="panel1a-header">
                    <LockOpenIcon className={classes.icon} />
                    <Typography className={classes.heading} style={{ marginLeft: '.5rem' }}>
                      <h6>
                        {' '}
                        <b>Changer mon mot de passe</b>
                      </h6>
                      Modifier le mot de passe de votre compte
                    </Typography>
                  </ExpansionPanelSummary>
                  <ExpansionPanelDetails>
                    <React.Fragment>
                      <form className={classes.form} onSubmit={handleValidSubmit}>
                        <Row className="justify-content-center">
                          <Col md="12">
                            <AvForm id="password-form">
                              <AvField
                                name="currentPassword"
                                label={translate('global.form.currentpassword.label')}
                                placeholder={translate('global.form.currentpassword.placeholder')}
                                type="password"
                                validate={{
                                  required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
                                }}
                              />
                              <AvField
                                name="newPassword"
                                label={translate('global.form.newpassword.label')}
                                placeholder={translate('global.form.newpassword.placeholder')}
                                type="password"
                                validate={{
                                  required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
                                  minLength: { value: 4, errorMessage: translate('global.messages.validate.newpassword.minlength') },
                                  maxLength: { value: 50, errorMessage: translate('global.messages.validate.newpassword.maxlength') },
                                }}
                                onChange={updatePassword}
                              />
                              <PasswordStrengthBar password={password} />
                              <AvField
                                name="confirmPassword"
                                label={translate('global.form.confirmpassword.label')}
                                placeholder={translate('global.form.confirmpassword.placeholder')}
                                type="password"
                                validate={{
                                  required: {
                                    value: true,
                                    errorMessage: translate('global.messages.validate.confirmpassword.required'),
                                  },
                                  minLength: {
                                    value: 4,
                                    errorMessage: translate('global.messages.validate.confirmpassword.minlength'),
                                  },
                                  maxLength: {
                                    value: 50,
                                    errorMessage: translate('global.messages.validate.confirmpassword.maxlength'),
                                  },
                                  match: {
                                    value: 'newPassword',
                                    errorMessage: translate('global.messages.error.dontmatch'),
                                  },
                                }}
                              />
                              <ThemeProvider theme={theme}>
                                <Button variant="contained" type="submit" onClick={handleDrawerClose}>
                                  <Translate contentKey="password.form.button">Save</Translate>
                                </Button>
                              </ThemeProvider>
                            </AvForm>
                          </Col>
                        </Row>
                      </form>
                    </React.Fragment>
                  </ExpansionPanelDetails>
                </ExpansionPanel>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </Grid>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={issnackopen}
        onClose={onSnackClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={issnackopen}>
          <div className={classes.paper}>
            {' '}
            <EmojiEmotionsIcon className={classes.icone} />
            <p>Modification effectuée avec succées</p>
            <Button className="btn-success" type="submit" onClick={onSnackClose}>
              Fermer
            </Button>
          </div>
        </Fade>
      </Modal>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={issnackpass}
        onClose={onSnackClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={issnackpass}>
          <div className={classes.paper}>
            {' '}
            <CheckBoxIcon className={classes.icone} />
            <p>votre mot de passe a été modifié avec succès</p>
            <Button className="btn-success" type="submit" onClick={onSnackClose}>
              Fermer
            </Button>
          </div>
        </Fade>
      </Modal>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={issnackpassincorrectopen}
        onClose={onSnackClosepass}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={issnackpassincorrectopen}>
          <div className={classes.paper}>
            {' '}
            <WarningIcon className={classes.warning} />
            <p>votre mot de passe est incorrect</p>
            <Button className="btn-success" type="submit" onClick={onSnackClosepass}>
              Fermer
            </Button>
          </div>
        </Fade>
      </Modal>
      {/* <SnackBarCustom isSnackOpen={issnackopen} handleClose={onSnackClose} textMessage={'Modification effectuée avec succées'} /> */}
    </React.Fragment>
  );
};

const mapStateToProps = ({ authentication, client }: IRootState) => ({
  cliententity: client.entity,
  isAuthenticated: authentication.isAuthenticated,
});

const mapDispatchToProps = { getEntity, getSession, savePassword, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DonneesPerso);

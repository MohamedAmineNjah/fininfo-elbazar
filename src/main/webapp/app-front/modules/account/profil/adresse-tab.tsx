import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import { RouteComponentProps } from 'react-router-dom';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { getSearchEntities, getEntities } from './adresse-tab.reducer';
import { IRootState } from '../../../shared/reducers';
import { ThemeProvider } from '@material-ui/core/styles';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import EmojiEmotionsIcon from '@material-ui/icons/EmojiEmotions';
import CreateOutlinedIcon from '@material-ui/icons/CreateOutlined';
import WhereToVoteOutlinedIcon from '@material-ui/icons/WhereToVoteOutlined';

import {
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  Grid,
  TextField,
  CircularProgress,
  Snackbar,
  Modal,
  Fade,
  Backdrop,
  Switch,
  RadioGroup,
  FormControlLabel,
  FormGroup,
  Radio,
} from '@material-ui/core';
import { Translate, translate } from 'react-jhipster';
import { Gouvernorats } from '../../../shared/json/gouvernorat.json';
import { Villes } from '../../../shared/json/villes.json';
import { Localites } from '../../../shared/json/localite.json';
import { TextValid } from '../register/register-components';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { IAdresse } from '../../../shared/model/adresse.model';
import AddIcon from '@material-ui/icons/Add';
import AccountCircleOutlinedIcon from '@material-ui/icons/AccountCircleOutlined';
import CallOutlinedIcon from '@material-ui/icons/CallOutlined';
import './profil.scss';
import { theme, useStyles } from './adresse-tab-style';
import { cleanEntity } from '../../../shared/util/entity-utils';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import { handleChangeChecked } from '../../../../app/shared/util/entity-ui-services';
import TabPanel from '../profil-main/tab-panel';
import { values } from 'lodash';
import { AdresseEdit } from './adresse-edit';

export interface IAdresseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const AdresseProfilFront = (props: any) => {
  const classes = useStyles();
  const [listVilles, setListVilles] = useState([]);
  const [listlocality, setListlocality] = useState([]);
  const [adresseListMock, setadresseListMock] = useState<IAdresse>(null);
  const [issnackopen, setissnackopen] = React.useState(false);
  const [issnackajout, setissnackajout] = React.useState(false);
  const [selectedRadio, setSelectedRadio] = React.useState('');
  const [valeurs, setvaleurs] = useState<IAdresse>({});
  const [errorCount, setErrorCount] = React.useState(0);
  const [gouvernoratt, setGouvernoratt] = useState(true);
  const [villee, setVillee] = useState(true);
  const [localityy, setLocalityy] = useState(true);
  const [principale, setPrincipale] = React.useState(false);

  const apiUrl = 'api/adresses/client';
  const apiUrl2 = 'api/adresses';
  const handlePrincipale = (event) => {
    setPrincipale( !principale );
  };
  const handelGouv = (event, gouv) => {
    setListVilles([]);
    setListlocality([]);
    setVillee(!villee);
    setLocalityy(!localityy);
    if (gouv) {
      const listeVilles = Villes.filter(ville => parseInt(ville.IDGouvernorat, 10) === gouv.value);
      setListVilles(listeVilles);
    }
  };

  const handelVille = (event, ville) => {
    setListlocality([]);
    setLocalityy(!localityy);
    if (ville) {
      const listlocalite = Localites.filter(Localite => parseInt(Localite.IDVille, 10) === parseInt(ville.ID, 10));
      setListlocality(listlocalite);
    }
  };
  useEffect(() => {
    window.scrollTo(0, 0)
    const requestUrl = `${apiUrl}`;
    const fetchData = async () => {
      const result = await axios.get<IAdresse>(requestUrl);
      result.data.forEach((element, index) => {
        if (element.principale) setSelectedRadio('adresse-' + index);
      });
      setadresseListMock(result.data);
    };

    fetchData();
  }, []);

  const onSnackClose = () => {
    setissnackopen(false);
    props.setrefresh()
  };

  const onSnackCloseAjout = () => {
    setissnackajout(false);
    props.setrefresh()
  };
 

  const handleNextSubmit = (event, accountId) => {
    event.preventDefault();
    if (errorCount === 0) {
      const data = { clientId: accountId };
      for (let i = 0; i < event.target.length - 1; i++) {
        if (event.target[i].name) {
          data[event.target[i].name] = event.target[i].value;
        }
      }
     data["principale"]=principale;
      axios.post(apiUrl2, cleanEntity(data)).then(() => {
        (document.getElementById('create-adresse-form') as any).reset();
        setVillee(!villee);
        setLocalityy(!localityy);
        setGouvernoratt(!gouvernoratt);
        setissnackajout(true);
      });
    }
  };
  const annulerAjout = () => {
    (document.getElementById('create-adresse-form') as any).reset();

    (document.getElementById('Gouvernorat') as any).value = '';
    (document.getElementById('Ville')as any).value = '';
    (document.getElementById('localite')as any).value = '';

  };

  const handelChangeRadio = event => {
    console.log(event.target.value);
    setSelectedRadio(event.target.value);
    console.log(selectedRadio);
  };

  const goNext = () => {
    props.handlenextsubmit(adresseListMock[selectedRadio[selectedRadio.length - 1]]);
  };

  return (
    <React.Fragment>
      {' '}
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <h3 className="commandeTitle">Mes Adresses</h3>
            </TableCell>
          </TableRow>
        </TableHead>
        <RadioGroup row name="civilite" value={selectedRadio} onChange={handelChangeRadio}>
          <TableBody>
            <TableRow>
              <TableCell>
                <ExpansionPanel>
                  <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1a-content" id="panel1a-header">
                    <Typography className={classes.heading}>
                      <h6>
                        <AddIcon style={{ marginRight: '.5rem', color: 'green' }} /> Ajouter une nouvelle adresse
                      </h6>
                    </Typography>
                  </ExpansionPanelSummary>
                  <ExpansionPanelDetails>
                    <Typography>
                      <form
                        id="create-adresse-form"
                        className={classes.form}
                        onSubmit={e => handleNextSubmit(e, props.accountdetailadresse.id)}
                      >
                        <Grid item xs={12} md={6}>
                          <FormGroup>
                            <FormControlLabel
                              control={
                                <Switch
                                  name="principale"
                                  color="primary"
                                  onChange={handlePrincipale}
                                />
                              }
                              label="Principale"
                            />
                          </FormGroup>
                        </Grid>
                        <Table>
                          <TableBody>
                            <TableRow>
                              <TableCell>
                                <React.Fragment>
                                  <Grid container spacing={2}>
                                    <Grid item xs={12} md={6}>
                                      <TextValid
                                        autoComplete="fname"
                                        name="nom"
                                        required
                                        fullWidth
                                        id="firstName"
                                        patter="^[a-zA-Z]+[a-zA-Z ]*$"
                                        errortext={translate('register.messages.validate.firstName.pattern')}
                                        label={translate('global.form.firstName.label')}
                                        autoFocus
                                        errorcount={errorCount}
                                        seterrorcount={setErrorCount}
                                      />
                                    </Grid>
                                    <Grid item xs={12} md={6}>
                                      <TextValid
                                        required
                                        fullWidth
                                        id="lastName"
                                        label={translate('global.form.lastName.label')}
                                        name="prenom"
                                        autoComplete="lname"
                                        patter="^[a-zA-Z]+[a-zA-Z ]*$"
                                        errortext={translate('register.messages.validate.lastName.pattern')}
                                        errorcount={errorCount}
                                        seterrorcount={setErrorCount}
                                      />
                                    </Grid>
                                    <Grid item xs={12} md={6}>
                                      <TextValid
                                        autoComplete="Avenue"
                                        name="adresse"
                                        required
                                        fullWidth
                                        id="avenue"
                                        label={translate('global.form.avenue.label')}
                                        autoFocus
                                        errorcount={errorCount}
                                        seterrorcount={setErrorCount}
                                      />
                                    </Grid>

                                    <Grid item xs={12} md={6}>
                                      <Autocomplete
                                        id="Gouvernorat"
                                        options={Gouvernorats}
                                        key={gouvernoratt.toString()}
                                        getOptionLabel={option => option.label}
                                        onChange={(event, value) => handelGouv(event, value)}
                                        renderInput={params => <TextField required {...params} name="gouvernorat" label="Gouvernorat" />}
                                      />
                                    </Grid>
                                    <Grid item xs={12} md={6}>
                                      <Autocomplete
                                        id="Ville"
                                        key={villee.toString()}
                                        options={listVilles}
                                        getOptionLabel={option => option.Ville}
                                        onChange={(event, value) => handelVille(event, value)}
                                        renderInput={params => <TextField required {...params} name="ville" label="Ville" />}
                                      />
                                    </Grid>
                                    <Grid item xs={12} md={6}>
                                      <Autocomplete
                                        id="localite"
                                        key={localityy.toString()}
                                        options={listlocality}
                                        getOptionLabel={option => option.Localite}
                                        renderInput={params => <TextField required {...params} name="localite" label="Cité" />}
                                      />
                                    </Grid>
                                    <Grid item xs={12} md={6}>
                                      <TextValid
                                        required
                                        fullWidth
                                        errorcount={errorCount}
                                        seterrorcount={setErrorCount}
                                        type="number"
                                        id="tel"
                                        label={translate('global.form.tel.label')}
                                        name="mobile"
                                        autoComplete="tel"
                                        patter="^\d{8,8}$"
                                        errortext={translate('global.messages.validate.tel.invalid')}
                                      />
                                    </Grid>
                                  </Grid>
                                  <Grid style={{ marginTop: '1rem' }}>
                                    <ThemeProvider theme={theme}>
                                      <Button variant="contained" type="submit">
                                        <Translate contentKey="password.form.button">Save</Translate>
                                      </Button>
                                    </ThemeProvider>
                                    <Button
                                      variant="contained"
                                      color="secondary"
                                      className="btn btn-success"
                                      style={{ marginLeft: '.2rem' }}
                                      onClick={annulerAjout}
                                    >
                                      Annuler
                                    </Button>
                                  </Grid>
                                </React.Fragment>
                              </TableCell>
                            </TableRow>
                          </TableBody>
                          <Modal
                            aria-labelledby="transition-modal-title"
                            aria-describedby="transition-modal-description"
                            className={classes.modal}
                            open={issnackajout}
                            onClose={onSnackCloseAjout}
                            closeAfterTransition
                            BackdropComponent={Backdrop}
                            BackdropProps={{
                              timeout: 500,
                            }}
                          >
                            <Fade in={issnackajout}>
                              <div className={classes.paper}>
                                {' '}
                                <CheckBoxIcon className={classes.icone} />
                                <p>Adresse ajoutée avec succées</p>
                                <Button className="btn-submit-bazar" onClick={onSnackCloseAjout} {...AdresseProfilFront}>
                                  Fermer
                                </Button>
                              </div>
                            </Fade>
                          </Modal>
                        </Table>
                      </form>
                    </Typography>
                  </ExpansionPanelDetails>
                </ExpansionPanel>
              </TableCell>
            </TableRow>
          </TableBody>

          {/* Update Adresse */}
          {adresseListMock && adresseListMock.length > 0 ? (
            <TableBody>
              {adresseListMock.map((adresseitem, i) => (
               <AdresseEdit ispanier={props.ispanier}  key={i} adresseitem={adresseitem} setissnackopen={setissnackopen} i={i} classes={classes} />
              ))}
            </TableBody>
          ) : (
            <div className={classes.root}>
              <CircularProgress color="secondary" />
            </div>
          )}
        </RadioGroup>
        {props.ispanier ? (
          <Grid container justify="center">
            <Grid item md={4} style={{ marginTop: '2%' }}>
              <Button onClick={goNext} fullWidth variant="contained" color="secondary" className={classes.submit}>
                {' '}
                {'continuer'}
              </Button>
            </Grid>
          </Grid>
        ) : null}
      </Table>
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
    </React.Fragment>
  );
};

const mapStateToProps = ({ authentication, adresse, client }: IRootState) => ({
  adresseList: adresse.entities,
  loading: adresse.loading,
  totalItems: adresse.totalItems,
  adresseentity: adresse.entity,
  isAuthenticated: authentication.isAuthenticated,
  cliententity: client.entity,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdresseProfilFront);

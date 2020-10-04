import React, { useState, useEffect } from 'react';
import {  CircularProgress, Grid, ThemeProvider, TextField, TableRow, TableCell, ExpansionPanel, ExpansionPanelSummary, FormControlLabel, Radio, Typography, ExpansionPanelDetails, FormGroup, Switch, Table, TableBody } from '@material-ui/core';
import { Link, RouteComponentProps } from 'react-router-dom';
import clsx from 'clsx';
import { Button
} from 'reactstrap';
import AccountCircleOutlinedIcon from '@material-ui/icons/AccountCircleOutlined';
import CallOutlinedIcon from '@material-ui/icons/CallOutlined';
import CreateOutlinedIcon from '@material-ui/icons/CreateOutlined';
import WhereToVoteOutlinedIcon from '@material-ui/icons/WhereToVoteOutlined';
import axios from 'axios';
import { Translate, translate } from 'react-jhipster';
import { theme } from './adresse-tab-style';
import { TextValid } from '../register/register-components';
import { Autocomplete } from '@material-ui/lab';
import { Gouvernorats } from '../../../shared/json/gouvernorat.json';
import { Villes } from '../../../shared/json/villes.json';
import { Localites } from '../../../shared/json/localite.json';
import { cleanEntity } from '../../../../app/shared/util/entity-utils';


export const AdresseEdit = props => {

    const [selectedVille,setselectedVille] = useState("")
    const [selectedLocale,setselectedLocal] = useState("")
    const [listVilles,setListVilles] = useState([])
    const [listlocality,setlistlocality] = useState([])
    const [refreshVille,setRefreshVille] = useState(true)
    const [principale,setPrincipale] = useState(true)
    const [refreshlocality,setRefreshlocality] = useState(true)
    const [errorCount,setErrorCount]= useState(0);
    const {adresseitem, i, classes } = props
    const apiUrl2 = 'api/adresses';
    const getDefaultvalueEdit = (optionsarray: any, label: any) => {
        const filtered = optionsarray.filter(o => o.label === label);
        return filtered[0];
    };
    const getDefaultvalueVilleEdit = (optionsarray: any, Ville: any) => {
        const filtered = optionsarray.filter(o => o.Ville === Ville);
        return filtered[0];
    };
    const getDefaultvalueLocaliteEdit = (optionsarray: any, Localite: any) => {
        const filtered = optionsarray.filter(o => o.Localite === Localite);
        return filtered[0];
    };
    useEffect(()=>{
       
        if(adresseitem){
            setPrincipale(adresseitem.principale);
            setselectedVille(adresseitem.ville);
            setselectedLocal(adresseitem.localite);
            setRefreshVille(!refreshVille);
            setRefreshlocality(!refreshlocality);
            const gouvObj=getDefaultvalueEdit(Gouvernorats, adresseitem.gouvernorat);
            const filtredVille= Villes.filter(o => parseInt(o.IDGouvernorat,10) === gouvObj.value);
            setListVilles(filtredVille);
            const filtredLocality= Localites.filter(o => o.IDVille === getDefaultvalueVilleEdit(Villes, adresseitem.ville).ID);
            setlistlocality(filtredLocality);
        }   
    },[adresseitem])
    const handelGouv=(event, gouv)=>{
        setselectedVille('')
        setselectedLocal('')
        setRefreshVille(!refreshVille);
        setRefreshlocality(!refreshlocality);
        if (gouv) {
          const listeVilles = Villes.filter(ville => parseInt(ville.IDGouvernorat, 10) === gouv.value);
          setListVilles(listeVilles);
        }
    }
    const handelVille=(event, ville)=>{
        
        setselectedLocal('')
        setRefreshlocality(!refreshlocality);
        if (ville) {
          const listlocalite = Localites.filter(Localite => parseInt(Localite.IDVille, 10) === parseInt(ville.ID, 10));
          setlistlocality(listlocalite);
        }
    }
    const handleEditAdresse = (event, dataAdresse) => {
        event.preventDefault();
        if(errorCount===0){
          const data = { ...dataAdresse };
    
          for (let j = 0; j < event.target.length - 1; j++) {
            if (event.target[j].name) {
              data[event.target[j].name] = event.target[j].value;
            }
          }
       data["principale"]=principale
          axios.put(apiUrl2, cleanEntity(data)).then(() => {
            props.setissnackopen(true);
          });
        }
    
        
      };
    return (
        <div>
        <TableRow key={`entity-${i}`}>
                  <TableCell>
                    <ExpansionPanel>
                      <ExpansionPanelSummary expandIcon={<CreateOutlinedIcon />} aria-controls="panel1a-content" id="panel1a-header">
                        {props.ispanier ? (
                          <FormControlLabel
                            value={'adresse-' + i}
                            control={
                              <Radio
                                onClick={event => {
                                  event.stopPropagation();
                                }}
                                required={true}
                              />
                            }
                            label=""
                          />
                        ) : null}
                        <Typography className={classes.heading}>
                          {adresseitem.principale ? (
                            <h6>
                              <WhereToVoteOutlinedIcon style={{ marginRight: '.5rem', color: 'green' }} /> Adresse principale
                            </h6>
                          ) : null}
                          <span>
                            {' '}
                            {adresseitem.adresse} {adresseitem.localite} {adresseitem.ville} {adresseitem.gouvernorat}{' '}
                            {adresseitem.codePostalCodePostal}
                          </span>
                          <br />
                        </Typography>
                      </ExpansionPanelSummary>
                      <ExpansionPanelDetails>
                        <Typography>
                          <Grid item xs={12} sm={6}>
                            <FormGroup>
                              <FormControlLabel
                                control={
                                  <Switch
                                    checked={principale}
                                    name="principale"
                                    color="primary"
                                    onChange={(event,value)=>{setPrincipale(!principale)}}
                                  />
                                }
                                label="Principale"
                              />
                            </FormGroup>
                          </Grid>
                          <Grid style={{ padding: '.2rem' }}>
                            <span style={{ padding: '.5rem' }}>
                              {' '}
                              <AccountCircleOutlinedIcon style={{ marginRight: '.5rem', color: '#777' }} /> {adresseitem.nom}{' '}
                              {adresseitem.prenom}{' '}
                            </span>
                          </Grid>
                          <Grid style={{ padding: '.2rem' }}>
                            {adresseitem.mobile !== null ? (
                              <span style={{ padding: '.5rem' }}>
                                <CallOutlinedIcon style={{ marginRight: '.5rem', color: '#777' }} />
                                {adresseitem.mobile}
                              </span>
                            ) : null}
                          </Grid>

                          <Table>
                            <TableBody>
                              <TableRow>
                                <TableCell>
                                    <div className={classes.root}>
                                    
                                    </div>
                                    <React.Fragment>
                                    <form
                                    id={'create-adresse-form-edit' + adresseitem.id}
                                    className={classes.form}
                                    onSubmit={e => handleEditAdresse(e, adresseitem)}
                                    >
                                    <input type="hidden" name="index-adresse" value={i} />
                                    <Grid container spacing={2}>
                                        <Grid item xs={12} sm={6}>
                                        <TextValid
                                            autoComplete="Avenue"
                                            name="adresse"
                                            required
                                            fullWidth
                                            defaultValue={adresseitem ? adresseitem.adresse : ''}
                                            id={'adresse' + i}
                                            label={translate('global.form.avenue.label')}
                                        />
                                        </Grid>
                            
                                        <Grid item xs={12} sm={6}>
                                        <Autocomplete
                                            id={'Gouvernorat' + adresseitem.id}
                                            options={Gouvernorats}
                                            defaultValue={getDefaultvalueEdit(Gouvernorats, adresseitem.gouvernorat)}
                                            getOptionLabel={option => option.label}
                                            onChange={(event, value) => handelGouv(event, value)}
                                            renderInput={params => <TextField {...params} required name="gouvernorat" label="Gouvernorat" />}
                                        />
                                        </Grid>
                                        <Grid item xs={12} sm={6}>
                                        <Autocomplete
                                            id={'Ville' + adresseitem.id}
                                            options={listVilles}
                                            key={refreshVille.toString()}
                                            defaultValue={getDefaultvalueVilleEdit(Villes, selectedVille)}
                                            getOptionLabel={option => option.Ville}
                                            onChange={(event, value) => handelVille(event, value)}
                                            renderInput={params => <TextField {...params} required name="ville" label="Ville" />}
                                        />
                                        </Grid>
                                        <Grid item xs={12} sm={6}>
                                        <Autocomplete
                                            id={'localite' + adresseitem.id}
                                            options={listlocality}
                                            key={refreshlocality.toString()}
                                            defaultValue={getDefaultvalueLocaliteEdit(Localites, selectedLocale)}
                                            getOptionLabel={option => option.Localite}
                                            renderInput={params => <TextField {...params} required name="localite" label="Cité" />}
                                        />
                                        </Grid>
                                        <Grid item xs={12} sm={6}>
                                        <TextValid
                                            required
                                            fullWidth
                                            name="mobile"
                                            defaultValue={adresseitem.mobile}
                                            type="number"
                                            id={'tel' + adresseitem.id}
                                            label={translate('global.form.tel.label')}
                                            autoComplete="tel"
                                            patter="^\d{8,8}$"
                                            errortext={translate('global.messages.validate.tel.invalid')}
                                            errorcount={errorCount}
                                            seterrorcount={setErrorCount}
                                        />
                                        </Grid>
                                    </Grid>
                            
                                    <Grid style={{ marginTop: '1rem' }}>
                                        <ThemeProvider theme={theme}>
                                        <Button variant="contained" type="submit">
                                            <Translate contentKey="password.form.button">Save</Translate>
                                        </Button>
                                        </ThemeProvider>
                                    </Grid>
                                    </form>
                                </React.Fragment>
                                </TableCell>
                              </TableRow>
                            </TableBody>
                          </Table>
                        </Typography>
                      </ExpansionPanelDetails>
                    </ExpansionPanel>
                  </TableCell>
                </TableRow>
            </div>
  );
};
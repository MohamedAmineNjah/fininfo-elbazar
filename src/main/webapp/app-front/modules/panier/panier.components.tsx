import React, { useState, useEffect } from 'react';
import { makeStyles,withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import {Gouvernorats} from '../../shared/json/gouvernorat.json';
import {Villes} from '../../shared/json/villes.json';
import {Localites} from '../../shared/json/localite.json';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import {green} from '@material-ui/core/colors';
import NumericInput from 'react-numeric-input';
import { Grid, Button, Container, CssBaseline, TextField, Paper, FormControlLabel, FormControl } from '@material-ui/core';
import { TextValid } from '../account/register/register-components';
import Autocomplete from '@material-ui/lab/Autocomplete';
import {AdresseProfilFront} from '../account/profil/adresse-tab'
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import { Row, Nav, Alert} from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import './panier.scss'
import FingerprintIcon from '@material-ui/icons/Fingerprint';
import LensIcon from '@material-ui/icons/Lens';
import AirportShuttleOutlinedIcon from '@material-ui/icons/AirportShuttleOutlined';
import MonetizationOnOutlinedIcon from '@material-ui/icons/MonetizationOnOutlined';
import EventAvailableOutlinedIcon from '@material-ui/icons/EventAvailableOutlined';
import AccountCircleOutlinedIcon from '@material-ui/icons/AccountCircleOutlined';
import AccountCircleRoundedIcon from '@material-ui/icons/AccountCircleRounded';
import LocationOnOutlinedIcon from '@material-ui/icons/LocationOnOutlined';
import AccountBalanceWalletOutlinedIcon from '@material-ui/icons/AccountBalanceWalletOutlined';

const StyledTableCell = withStyles((theme) => ({
    head: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    body: {
      fontSize: 14,
    },

  }))(TableCell);
  
  const StyledTableRow = withStyles((theme) => ({
    root: {
      
    },
  }))(TableRow);
  
const useRowStyles = makeStyles({
  
    root: {
      '& > *': {
        borderBottom: 'unset',
      },
    },
    media:{
        width: "70px",
    },
    colWidth50 :{
        width: '50%'
    },
    colWidth20 :{
        width: '20%',
        padding: "2%"
    },
    colWidth15 :{
        width: '15%'
    },
    fieldQte:{
      marginLeft:'5px',
      width: '20%',
      marginTop: '10px'
    },
    inputQte:{
        "& input":{
          width:"50px",
            padding: '6.5px 15px'
      }
    },
    iconInfo:{
    
      marginRight:"4%",
      fontSize: "1.2rem",
      color : 'green',

    }
  });
  const IsPromo = product => {
    
    if(!product.debut_promo || !product.fin_promo || !product.remise|| !product.eligible_Remise || product.remise===0 ){
      return false
    }else{
      const currentDate= new Date();
      const startDate= new Date(product.debut_promo);
      const endDate= new Date(product.fin_promo);
      currentDate.setHours(0,0,0,0)
        startDate.setHours(0,0,0,0)
        endDate.setHours(0,0,0,0)
      const isPromotion = (startDate.getTime() <= currentDate.getTime())&&(currentDate.getTime()<=endDate.getTime()) 
      return isPromotion
    }
   
  };
export const RowProd = props => {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [isInPromo, setIsInPromo] = React.useState(false);
    const [prixProd,setPrixProd]= React.useState(row.prix_ttc);
    const prixtot=(row.prix_ttc*row.qte).toFixed(3) 
    const [prixtotal, setPrixtotal] = React.useState(prixtot);
    const [qte, setQte] = React.useState(row.qte);
    const classes = useRowStyles();

    useEffect(() => {
      const isPro=IsPromo(row)
      setIsInPromo(isPro)
      if(isPro) {
        const prixp=(row.prix_ttc-((row.prix_ttc*row.remise)/100)).toFixed(3);
        console.log(prixp)
        setPrixProd(prixp);
        setPrixtotal((prixp as any*row.qte).toFixed(3) as any)
      }
    }, [row]);

    const handleChangeQte = (event)=>{
    
      let value=event.target.value
    
      if(event.target.value>row.quantite_vente_max){
        event.target.value=row.quantite_vente_max;
        value=row.quantite_vente_max;
      }
      if(parseInt(value,10)===0){
      
        event.target.value=1;
        value=1;
        
      }
      
      setPrixtotal((prixProd*value).toFixed(3)as any)
    }
    const handlefocusOut = (event)=>{
      const value=event.target.value
      const arr=[...props.listproduits]
      arr.map(item =>{
        if(item.id===row.id){
          item.qte=parseInt(value,10);
        }
      })
      props.setlistproduits(arr)
      props.edititempanier(arr)
      props.calculetotal(arr)
    }
    useEffect(() => {
      const number = document.getElementById('qte'+row.id);
    
      number.onkeydown = function(e:any) {
        if(!((e.keyCode > 95 && e.keyCode < 106)
        || (e.keyCode > 47 && e.keyCode < 58) 
        || e.keyCode === 8 )&& ( e.keyCode!==38 && e.keyCode!==39 && e.keyCode!==40 && e.keyCode!==37 )|| (e.keyCode===54)) {
         
            return false;
        }
        if(!isNaN(e.key)){
        if(parseInt(e.target.value+e.key ,10) > row.quantite_vente_max ){
          e.target.value =row.quantite_vente_max;
        }
        if(parseInt(e.target.value+e.key ,10) === 0 ){
          console.log(e.target.value+e.key)
          e.target.value =1;
         return false
        }
        if(parseInt(e.target.value ,10) === 0 ){
          console.log(e.target.value)
          e.target.value =1;
         return false
        }
      }
      if(e.target.value.length===1 && e.key=== "Backspace" ){
        e.target.value =1;
       return false
      }
    }
    }, [row]);
    const removePoduit = ()=>{
      let arr=[...props.listproduits]

        arr=arr.filter(item =>{
          return item.id !== row.id
        });
        props.setlistproduits(arr)
        props.edititempanier(arr)
        props.calculetotal(arr)
    }
    return (
      <React.Fragment>
           
      <div className="row my-2 text-capitalize text-center bordredItem" >

      <div className="col-10 mx-auto col-lg-2">
      
          <img src={"media/"+ row.image_url} style={{height:'4rem'}} className="img-fluid" />
      </div>
      <div className="col-10 mx-auto col-lg-3">
          <span className="d-lg-none nomProduit">Produit : </span>
           <span className="nomProduit"> {row.nom} </span>
          <div className="mx-1 " > <span className="sousCategory">{row.souscategory}</span></div>
      </div>
      <div className="col-10 mx-auto col-lg-2" >
      {isInPromo ? (
        <div>
        <p className="red" style={{"marginBottom": "auto"}} >{parseFloat(prixProd).toFixed(3)+" DT"}</p> 
        <p className="" style={{"textDecoration":"line-through","color": "#adb5bd"}}>{parseFloat(row.prix_ttc).toFixed(3)+" DT"}</p> 
        
      </div>
        
      ):
      (
        <div className="noPromo">
         <span className="red" style={{"margin":"auto"}} >{parseFloat(row.prix_ttc).toFixed(3) +" DT"}</span> 
         </div>
      )
      }
      </div>
      <div className="col-10 mx-auto col-lg-1 my-2 my-lg-0">

          <div className="d-flex justify-content-center">
              <div>
                  
                  <span className="btn-black mx-3 " >

                  <TextField 
              id={"qte"+row.id} 
              defaultValue={qte}
              onChange={handleChangeQte}
              name="quantite"
              onBlur={handlefocusOut}
              InputProps={{ className: classes.inputQte, inputProps: { 
                  max: row.quantiteVenteMax, min: 1 
              }}} 
              type="number" 
              variant="outlined" 
              />  
                  </span>
                  
              </div>
          </div>
      </div>
      {/**/}
     
      <div className="col-10 mx-auto col-lg-3">
        <strong  className="nomProduit">Total : </strong> 
        <span className='red'>{prixtotal+" DT"}</span> 
      </div>
      <div className="col-10 mx-auto col-lg-1">
        <div className="cart-icon">
        <FontAwesomeIcon onClick={removePoduit} icon="trash" style={{color:'grey'}}/>
        </div>
      </div>
  </div>
  </React.Fragment>
    );
  
}
export const InfoLivraison = props => {
    const { total } = props;
    const [open, setOpen] = React.useState(false);
    
    const classes = useRowStyles();

    return (
        <React.Fragment>
            <Grid container justify="center" >
              <Grid container >
                <Grid item xs={12} style={{"padding":'2%'}}  >
                  <h6 className='nomProduit'><FontAwesomeIcon icon="shipping-fast" className={classes.iconInfo}/>{"Livraison Express"}</h6>
                </Grid>
                <Grid item xs={12} style={{"padding":'2%'}}>
                <h6 className='nomProduit'><FontAwesomeIcon icon="handshake" className={classes.iconInfo}/>{"Paiement à la livraison"}</h6>
                </Grid>
                <Grid item xs={12} style={{"padding":'2%'}}>
                <h6 className='nomProduit'><FontAwesomeIcon icon="credit-card" className={classes.iconInfo}/>{"Possibilité de payer avec carte de crédit"}</h6>
                </Grid> 
              </Grid>
            </Grid>
        </React.Fragment>
    );
  
}
const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  adressContent:{
    display: "flex",
    verticalAlign: "top"
  },
  root: {
    flexGrow: 1,
  },
  recapItem:{
    'margin-left': '25%'
  },
  recapTitle:{
    'float':'left'
  },
  avatar: {
    margin: theme.spacing(2),
    backgroundColor: theme.palette.secondary.main,
  },
  pageTitle: {
    color: "#6c757d",
    fontWeight:"bold",
    textAlign:'center',
    marginTop:"2%"
  },
  date: {
    width: '100%', // Fix IE 11 issue.
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  table: {
    border:"1px solid #dee2e6",
    overflowY:"scroll",
    maxHeight: "331px",
    display:"block",
    marginBottom: "2%"
  },
  cardInfo:{
    marginTop:"1.5%",
    padding:'2%',
 
    border:"1px solid #cecece"
  },
  boxPayment:{
  
    padding: '1%',
    textAlign:"center"
  },
  submit: {
    'background-color':green[400],
    '&:hover': {
      'background-color': green[500],
   },
    'margin': theme.spacing(0, 0, 2),
  },
}));

export const ConfirmerInfoAdresse = props => {
 
  const classes = useStyles();
  const classesrow = useRowStyles();
  const [refresh, setRefresh] = useState(true);
  const handleChangeRefreshAdresse=() => {
    setRefresh(!refresh);
  };
  const handleNextSubmit = (newAdress) => {
    event.preventDefault();
    const vil=Villes.filter(item=>item.Ville===newAdress.ville)
    const data={ville:vil[0].ID,val:props.prixtotal,idClient:props.account.id}
    props.getlivraisonparam(data)
    props.setadresseprincipal(newAdress);
    props.setstep(2);
  }
  
  return (
    <Grid container style={{padding:"2.5%"}} spacing={2}>
      <Grid item md={2}>
    <a>    
      <Typography variant="subtitle1"  onClick={props.setstep.bind(this,0)} style={{"color":'#6c757d',"fontWeight":"bold"}} gutterBottom> <FontAwesomeIcon icon={"arrow-left"} /> {"Retour"} </Typography>         
    </a>
  </Grid> 
      <Grid item md={8} className={classes.adressContent}>
      
      </Grid>
      <Grid item md={8}>
       <AdresseProfilFront key={refresh} setrefresh={handleChangeRefreshAdresse} idadresse={props.adresseentity} handlenextsubmit={handleNextSubmit} accountdetailadresse={props.account} ispanier={true}/>
      </Grid>
     
      <Grid item md={4}>
  <TableContainer className={classes.cardInfo}  component={Paper}>
    <Table className={classes.table} aria-label="customized table">
      <TableBody>
        {props.listproduits.map((row) => (
          <StyledTableRow  key={row.id}>
            <Grid container>
              <Grid item xs={3}>
                  <img className={classesrow.media} src={"media/"+ row.image_url}></img>
         
              </Grid>
              <Grid container item xs={9} style={{padding: "10px"}}>
                <Grid  item xs={12}>
                  <Typography variant="h6" style={{"color":"#6c757d","fontWeight":"bold","fontSize":"13px"}} >{row.nom}<span style={{"color":"#212529"}}>{" X "+ row.qte}</span></Typography>
                </Grid>
                <Grid  item xs={12}>
                  {IsPromo(row) ? (
                    <Typography variant="subtitle1" style={{"color":"#e21717","fontWeight":"bold"}} >
                      {((row.prix_ttc-((row.prix_ttc*row.remise)/100))*row.qte).toFixed(3) +" DT" }
                    </Typography>
                      
                    ):
                    (
                      <Typography variant="subtitle1" style={{"color":"#e21717","fontWeight":"bold"}} >
                      {(row.prix_ttc*row.qte).toFixed(3) +" DT" }
                    </Typography>
                    )
                  }
                </Grid>
              </Grid>
            </Grid>
          </StyledTableRow>
        ))}
      </TableBody>
    </Table>
    <Grid  container item md={12} style={{padding:"0.5%"}} justify="center">
      <Grid container item xs={9}>
        <Grid item xs={6}>
          <Typography variant="subtitle2" style={{"color":'#6c757d',"fontWeight":"bold","fontSize":"1em"}} gutterBottom>{"Montant total :"}</Typography>
        </Grid>
        <Grid item xs={6} >
          <Typography variant="subtitle2" style={{"color":'#e21717',"fontWeight":"bold","fontSize":"1em"}} gutterBottom>{props.prixtotal+" DT TTC"}</Typography>
        </Grid>
      </Grid>
    </Grid>
  </TableContainer>
  </Grid>
  </Grid>
  )
}
export const ValidationFinal = props =>{
  const classes = useStyles();
  const [modepayment, setModepayment] = React.useState('Cash');
  const [montantTotal, setMontantTotal] = React.useState(0);
  const [dateLivraison, setDateLivraison] = React.useState("");
  const convertDate=(inputFormat) => {
    const pad=(s)=>{ return (s < 10) ? '0' + s : s; }
    const d = new Date(inputFormat)
    return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('/')
  }
  const convertDateForBack=(inputFormat) => {
    const pad=(s)=>{ return (s < 10) ? '0' + s : s; }
    const d = new Date(inputFormat)
    return [ d.getFullYear(), pad(d.getMonth()+1),pad(d.getDate())].join('-')
  }
  const handleChange = (event) => {
    setModepayment(event.target.value);
  };
  const sendCommande=()=>{
    let totalHt=0;
    let totalTva=0;
    let totalRemise=0;
    const currentDate= new Date();
    let dateLivre=null;
    if(props.livraisonparam){
      dateLivre=new Date()
      dateLivre.setTime(dateLivre.getTime()+(props.livraisonparam.date*60*60*1000))
    }else{
      dateLivre=null;
    }
    const listCommande =[];
  
    props.listproduits.map((row) => {
      totalHt=totalHt+(row.prix_ht*row.qte);
      let prixTTC=null
      let remise=0;
      if(IsPromo(row)){
        const dateDebut=new Date(row.debut_promo);
        const dateFin=new Date(row.fin_promo);
        currentDate.setHours(0,0,0,0)
        dateDebut.setHours(0,0,0,0)
        dateFin.setHours(0,0,0,0)
        
        if(currentDate>=dateDebut && currentDate<=dateFin && row.eligible_Remise===true && row.remise>0){
          prixTTC=(((row.prix_ttc-((row.prix_ttc*row.remise)/100))*row.qte)).toFixed(3)
          totalRemise=totalRemise+((row.prix_ttc-((row.prix_ttc*row.remise)/100))*row.qte)
          remise=row.remise;
        }
       }else{
        prixTTC=(row.prix_ttc*row.qte).toFixed(3)
        remise=0;
      }
      listCommande.push({
        "quantite": row.qte,
        "prixHT": row.prix_ht,
        remise,
        "tva": row.taux_tva,
        "refProduitNom":row.nom,
        prixTTC,
        "refCommandeId": null,
        "refCommandeReference": null,
        "refProduitId": row.id,
        "refProduitReference": row.reference
      });
    });
    if(props.livraisonparam){
      totalHt=totalHt+(props.livraisonparam.frais/1.13)
    }else{
      totalHt=totalHt+(20/1.13)
    }
    
    totalTva=montantTotal-totalHt

    const commande ={
      statut:"Reservee",
      origine: "Client",
      totalHT: (totalHt).toFixed(3),
      totalTVA: (totalTva).toFixed(3),
      totalRemise: totalRemise.toFixed(3),
      totTTC:montantTotal,
      devise:"TND",
      reglement: modepayment,
      dateLivraison:convertDateForBack(dateLivre),
      dateCreation:convertDateForBack(currentDate),
      idClientId:props.account.id,
      nomClient:props.account.lastName,
      prenomClient:props.account.firstName,
      prenom:props.adresseprincipal.prenom,
      fraisLivraison:props.livraisonparam.frais.toFixed(3),
      nom:props.adresseprincipal.nom,
      adresse:props.adresseprincipal.adresse,
      gouvernorat:props.adresseprincipal.gouvernorat,
      ville:props.adresseprincipal.ville,
      localite:props.adresseprincipal.localite,
      codePostal:props.adresseprincipal.codePostalCodePostal,
      indication:props.adresseprincipal.indication,
      telephone: props.adresseprincipal.telephone,
      mobile: props.adresseprincipal.mobile,
      idAdresseId: props.adresseprincipal.id,
      zoneId: props.livraisonparam ? props.livraisonparam.zoneId : "",
      zoneNom: props.livraisonparam ? props.livraisonparam.zoneNom : ""
    }
    const data = {
      commandeDTO: commande,
      commandeLignes: listCommande,
    };
    props.sendcommande(data,[...props.listproduits])
    props.setstep(3);
  }
  useEffect(() => {
    window.scrollTo(0, 0)
    if(props.livraisonparam){
      console.log((parseFloat(props.livraisonparam.frais)+parseFloat(props.prixtotal)).toFixed(3));
      setMontantTotal((parseFloat(props.livraisonparam.frais)+parseFloat(props.prixtotal)).toFixed(3) as any)
    }else{
      setMontantTotal((20+parseFloat(props.prixtotal)).toFixed(3) as any)
    }
    let date = null;
    if(props.livraisonparam){
       date= new Date();
      date.setTime(date.getTime()+(props.livraisonparam.date*60*60*1000))
      setDateLivraison(convertDate(date))
    }
    

   
  }, [props.livraisonparam,props.prixtotal]);

  const classesrow = useRowStyles();
  return (
  <Grid container style={{padding:"2.5%"}} spacing={1}>
   <Grid item md={2}>
    <a>    
      <Typography variant="subtitle1"  onClick={props.setstep.bind(this,1)} style={{"color":'#6c757d',"fontWeight":"bold"}} gutterBottom> <FontAwesomeIcon icon={"arrow-left"} /> {"Retour"} </Typography>         
    </a>
  </Grid> 
  <Grid item md={8}>
  <Typography variant="h5" className={classes.pageTitle}  > Vue global sur la commande </Typography>
  </Grid>
  <Grid item md={8}>
    <TableContainer className={classes.cardInfo}  component={Paper}>
    <Grid item sm={12}>
    {!props.livraisonparam ? ( 
      
        <Alert color="danger" style={{margin:"auto"}}>
        {"Votre zone n'est prise en charge par notre service de livraison pour l'instant"}
        </Alert>
   
  ):(
    <Grid item sm={12}>
    <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold"}} gutterBottom>
      {"Votre Adresse :"+props.adresseprincipal.adresse+", "+props.adresseprincipal.localite+" "+props.adresseprincipal.ville+", "+props.adresseprincipal.gouvernorat}
      </Typography> 
      <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold"}} gutterBottom>{"Date de livraison : "+dateLivraison}</Typography>   
      <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold"}} gutterBottom>{"Mode de paiement : "}</Typography>     
    
      <FormControl component="fieldset">
        <RadioGroup aria-label="gender" value={modepayment} onChange={handleChange} name="customized-radios">
          <Grid container justify="center" item md={12} spacing={2} style={{"alignItems": "start"}}>
            <Grid container item md={4} justify="center" >
              <FormControlLabel value="Cash" style={{margin: "1%"}} control={<Radio  />} label="" />
              <Grid item md={12} className={classes.boxPayment}>
                <Typography variant="h6" style={{"fontWeight":"bold",color: "#28a745"}} gutterBottom>{"Espéce"}</Typography> 
                <FontAwesomeIcon icon="coins" style={{fontSize: "30px",color: "#28a745",margin:'4%'}} />
                <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold",fontSize: "12px"}} gutterBottom>{"Assurez-vous de préparer le montant exact de la commande en dinars"}</Typography> 
                <FontAwesomeIcon icon="check-circle" style={{fontSize: "30px",color: "#28a745",margin:'4%',opacity: modepayment==="Cash" ? "1":"0"}} />
                
              </Grid>
            </Grid>
            <Grid container item md={4} justify="center" >
              <FormControlLabel value="Cheque" style={{margin: "1%"}}  control={<Radio  />} label="" />
              <Grid item md={12} className={classes.boxPayment}>
                <Typography variant="h6" style={{"fontWeight":"bold",color: "#28a745",textAlign:"center"}} gutterBottom>{"Cheque"}</Typography> 
               
                <FontAwesomeIcon icon="money-check" style={{fontSize: "30px",color: "#28a745",margin:'4%'}} />
                <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold",fontSize: "12px"}} gutterBottom>{"Merci de bien mentionner le nom de la société el bazar dans le libelé"}</Typography> 
                <FontAwesomeIcon icon="check-circle" style={{fontSize: "30px",color: "#28a745",margin:'4%',opacity: modepayment==="Cheque" ? "1":"0"}} />
              </Grid>
            </Grid>
            <Grid container item md={4} justify="center" >
              <FormControlLabel value="CarteBancaire" style={{margin: "1%"}}  control={<Radio  />} label="" />
              <Grid item md={12} className={classes.boxPayment}>
                <Typography variant="h6" style={{"fontWeight":"bold","color": "#28a745",textAlign:"center"}} gutterBottom>{"Carte bancaire"}</Typography> 
                <FontAwesomeIcon icon="credit-card" style={{fontSize: "30px",color: "#28a745",margin:'4%'}} />
                <Typography variant="subtitle1" style={{"color":'#6c757d',"fontWeight":"bold",fontSize: "12px"}} gutterBottom>{"Un Terminal de paiment (TPE) sera mis à votre dispostion par le livreur"}</Typography> 
                <FontAwesomeIcon icon="check-circle" style={{fontSize: "30px",color: "#28a745",margin:'4%',opacity: modepayment==="CarteBancaire" ? "1":"0"}} />
              </Grid>
            </Grid>
          </Grid>
        </RadioGroup>
      </FormControl>
      <Grid item md={12} style={{marginTop: "3%"}}>
        <Button onClick={sendCommande}  fullWidth variant="contained" color="secondary" className={classes.submit} > {"Confirmer commande"}</Button>
      </Grid>
      </Grid>  
    )}
    </Grid>
  
    </TableContainer>
    
  </Grid>
  {props.livraisonparam ? (
  <Grid item md={4}>
  <TableContainer className={classes.cardInfo}  component={Paper}>
    <Table className={classes.table} aria-label="customized table">
      <TableBody>
        {props.listproduits.map((row) => (
          <StyledTableRow  key={row.id}>
            <Grid container>
              <Grid item xs={3}>
                  <img className={classesrow.media} src={"media/"+ row.image_url}></img>
         
              </Grid>
              <Grid container item xs={9} style={{padding: "10px"}}>
                <Grid  item xs={12}>
                  <Typography variant="h6" style={{"color":"#6c757d","fontWeight":"bold","fontSize":"13px"}} >{row.nom}<span style={{"color":"#212529"}}>{" X "+ row.qte}</span></Typography>
                </Grid>
                <Grid  item xs={12}>
                  {IsPromo(row) ? (
                    <Typography variant="subtitle1" style={{"color":"#e21717","fontWeight":"bold"}} >
                      {((row.prix_ttc-((row.prix_ttc*row.remise)/100))*row.qte).toFixed(3) +" DT" }
                    </Typography>
                      
                    ):
                    (
                      <Typography variant="subtitle1" style={{"color":"#e21717","fontWeight":"bold"}} >
                      {(row.prix_ttc*row.qte).toFixed(3) +" DT" }
                    </Typography>
                    )
                  }
                </Grid>
              </Grid>
            </Grid>
          </StyledTableRow>
        ))}
      </TableBody>
    </Table>
    <Grid  container item md={12} style={{padding:"0.4%"}} justify="center">
    <Grid container item xs={12} sm={9}>
        <Grid item xs={6} md={7}>
          <Typography variant="subtitle2" style={{"color":'#6c757d',"fontWeight":"bold","fontSize":"15px"}} gutterBottom>{"Frais de livraison :"}</Typography>
        </Grid>
        <Grid item xs={6} md={5}>
          <Typography variant="subtitle2" style={{"color":'#495057',"fontWeight":"bold","fontSize":"15px"}} gutterBottom>{(props.livraisonparam && props.livraisonparam.frais) ? props.livraisonparam.frais.toFixed(3)+" DT" : null}</Typography>
        </Grid>
      </Grid>
      <Grid container item xs={12} sm={9}>
        <Grid item xs={6} md={7}>
          <Typography variant="subtitle2" style={{"color":'#6c757d',"fontWeight":"bold","fontSize":"15px"}} gutterBottom>{"Montant produit :"}</Typography>
        </Grid>
        <Grid item xs={6} md={5}>
          <Typography variant="subtitle2" style={{"color":'#495057',"fontWeight":"bold","fontSize":"15px"}} gutterBottom>{props.prixtotal+" DT"}</Typography>
        </Grid>
      </Grid>
      <Grid container item xs={12} sm={9}>
        <Grid item xs={6}>
          <Typography variant="subtitle2" style={{"color":'#6c757d',"fontWeight":"bold","fontSize":"1em"}} gutterBottom>{"Montant total :"}</Typography>
        </Grid>
        <Grid item xs={6}>
          <Typography variant="subtitle2" style={{"color":'#e21717',"fontWeight":"bold","fontSize":"1em"}} gutterBottom>{montantTotal+" DT TTC"}</Typography>
        </Grid>
      </Grid>
    </Grid>
  </TableContainer>
  </Grid>
  ):null}
</Grid>
  )
}
const useStylesRecap = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    boxShadow:'none'
  },
  cardInfo:{
    marginTop:"1.5%",
    padding:'2%',
  
  },
  submit: {
    'background-color':green[400],
    textTransform: "capitalize",
    '&:hover': {
      'background-color': green[500],
   },
    'margin': theme.spacing(0, 0, 2),
  },
}));
export const Recap = props =>{
  
  const listProd=props.listproduits
  const [isClear, setisClear] = useState(false);
  const [modePaiment, setModePaiment] = useState("");
  const classes = useStylesRecap();
  let totalHt =0
  listProd.map((row) => {
    totalHt=totalHt+(row.prix_ht*row.qte);
  })
  if( props.livraisonparam){
    totalHt=totalHt+(props.livraisonparam.frais/1.13);
  }else{
    totalHt=totalHt+(20/1.13);
  }
  const convertDate=(inputFormat) => {
    const pad=(s)=>{ return (s < 10) ? '0' + s : s; }
    const d = new Date(inputFormat)
    return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('/')
  }
  useEffect(()=>{
    window.scrollTo(0, 0);
    if(props.commanderep){
      switch(props.commanderep.reglement){
        case ("Cash"):
        setModePaiment("espèces");
        break;
        case ("Cheque"):
        setModePaiment("chèque");
        break;
        case ("CarteBancaire"):
        setModePaiment("carte bancaire");
        break;
        default :
        setModePaiment("espèces");
        break;
      }
    }
  },[])
  
  const IsPromoRecap = product => {
    const currentDate= new Date();
    const startDate= new Date(product.debut_promo);
    const endDate= new Date(product.fin_promo);
    currentDate.setHours(0,0,0,0)
    startDate.setHours(0,0,0,0)
    endDate.setHours(0,0,0,0)
    
    const isInPromo = (startDate.getTime() <= currentDate.getTime())&&(currentDate.getTime()<=endDate.getTime()&& product.eligible_Remise===true && product.remise>0)
    if (isInPromo) {
      return (
        <Grid item sm={2} xs={12}>
                <span style={{color :'#777' , fontWeight:'bold'}}>Prix : </span>
                <span style={{color:'red' ,fontWeight:'bold'}}>{ ((product.prix_ttc-(product.prix_ttc*product.remise/100))*product.qte).toFixed(3)  +" DT "}</span>
              </Grid>
      );
    } else {
      return (
        <Grid item sm={2} xs={12}>
          <span style={{color :'#777' , fontWeight:'bold'}}>Prix : </span>
          <span style={{color:'red' ,fontWeight:'bold'}}>{ (product.prix_ttc*product.qte).toFixed(3)  +" DT "}</span>
        </Grid>
      );
    }
  };

  return (
    <Container component="main"  style={{paddingTop:'20px'}}>
      <Grid container item xs={12}>
        <Alert style={{"width":"100%"}}>
          <Grid container justify='center'  >
            <Grid item xs={8} container justify='center'>
              <img src="content/images/happy.png" width="120px"/>
            </Grid>
            <Grid item xs={12} style={{marginBottom:'1rem'}}  container justify='center'>
              <span style={{color:'green'}}> Votre Commande a été enregistrée</span>
            </Grid>
          </Grid>
        </Alert>
      </Grid>
      <Grid container justify="center" style={{"border":"1px solid #adb5bd","borderRadius": "5px","marginBottom": '1%'}} >
        <Grid item container spacing={3} justify="center" style={{marginTop:"1rem"}}>
          <h5 style={{color :'#E82121',fontWeight :'bold', padding:'1rem'}} className='titre'>Récapitulatif de la commande</h5>
        </Grid>
        <Grid item container spacing={3} justify="center" className='font'>
          <Grid item container direction='column' xs={12} sm={4} style={{marginLeft: "13%"}}>
            <Grid container >
              <Grid item >
                <AccountCircleRoundedIcon  style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Nom : </span>
                <span className={"textRecap"}>{props.adresseprincipal.nom} </span>
              </Grid>
            </Grid>
            <Grid container >
              <Grid item >
                <AccountCircleOutlinedIcon  style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Prénom : </span>
                <span className={"textRecap"}>{props.adresseprincipal.prenom}</span>
              </Grid>   
            </Grid>
            <Grid container >
              <Grid item xs={12} sm={12}>
                <AccountBalanceWalletOutlinedIcon  style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Type de paiement : </span>
                <span className={"textRecap"}> {modePaiment}</span>
              </Grid> 
            </Grid>
            <Grid container >
              <Grid item xs={12} sm={12}>
                <LocationOnOutlinedIcon  style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Adresse : </span>
                <span className={"textRecap"}> {props.adresseprincipal.adresse+", "+props.adresseprincipal.localite+" "+props.adresseprincipal.ville+", "+props.adresseprincipal.gouvernorat}</span>
              </Grid>
            </Grid>
          </Grid>
          <Grid  container item direction='column' xs={12} sm={4} className="padrecap" >
            <Grid container > 
              <Grid item xs={12} sm={12}>
                <LensIcon style={{color:'#EAC02E', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Etat : </span>
                <span className={"textRecap"}>Commandé</span>
              </Grid>
            </Grid>
            <Grid container >
              <Grid item xs={12} sm={12}>
                <FingerprintIcon style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Référence : </span>
                <span className={"textRecap"}>{props.commanderep.reference}</span>
              </Grid>
            </Grid>
            <Grid container >
              <Grid item xs={12} sm={12}>
                <AirportShuttleOutlinedIcon style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Frais de livraison : </span>
                <span className={"textRecap"}>{props.livraisonparam.frais ? props.livraisonparam.frais.toFixed(3) + ' DT' : '0.000'}</span>
              </Grid>
            </Grid>
            <Grid container >
              <Grid item xs={12} sm={12}>
                <EventAvailableOutlinedIcon style={{color:'grey', marginRight:'.5rem'}}/>
                <span className={"titleRecap"}>Date de livraison : </span>
                <span className={"textRecap"}>{props.commanderep.dateLivraison ? convertDate(props.commanderep.dateLivraison) : ""}</span>
              </Grid>
            </Grid>
          </Grid>
          <Grid container item xs={12} sm={7} justify="center">
            <Grid item xs={11} sm={8}>
              <MonetizationOnOutlinedIcon style={{color:'grey', marginRight:'.5rem'}}/>
              <span className={"titleRecap"}>Montant à payer : </span>
              <span style={{color :'#E82121',fontWeight:"bold", padding:'1rem'}}>{props.commanderep.totTTC ? props.commanderep.totTTC.toFixed(3)+" DT TTC" : " "}</span>
            </Grid>
          </Grid>
              
        </Grid>
        <Grid container item xs={11} sm={7} direction='row' spacing={3 } justify='center' alignItems='center' style={{marginTop:'1rem',marginBottom:'1rem',borderTop:"1px solid #adb5bd"}} >
          <h5 style={{color :'#E82121',fontWeight:"bold", padding:'1rem'}} className='titre'>La liste des produits</h5>
        </Grid>
        <Grid container item xs={12}justify='center' spacing={3} style={{marginBottom:"1rem"}} >
          {listProd.map((row , i) => (
             <Grid key={i} container item xs={12} direction='row' justify='center' className="bordredItem"  >
            <Grid container   item sm={12} xs={12} direction='row' spacing={3 } justify='center' alignItems='center' >
              <Grid item  sm={3} xs={12}>
                <span style={{color :'#777' , fontWeight:'bold'}}>Reférence : </span>
                  <span style={{color:'#777'}}>{row.reference}</span>
              </Grid>
              <Grid item sm={6} xs={12}>
                <span style={{color :'#212529' , fontWeight:'bold'}}> {row.nom+' X '}</span>
                <span style={{color:'#777'}}>{row.qte}</span>
              </Grid>
              {IsPromoRecap(row)}
            </Grid>
            </Grid>
          ))}
        </Grid>
      </Grid>
      <Nav id="header-tabs" style={{"width":"30%","margin": "auto"}} navbar>
      <NavItem>
        <NavLink tag={Link} to="/" className="d-flex align-items-center">
          <Button fullWidth  variant="contained" color="secondary"  className={classes.submit}> 
            <FontAwesomeIcon icon="home" /> 
            <span style={{marginLeft:'8px',marginTop:'3px'}}>Acceuil  </span>
          </Button>
        </NavLink>
      </NavItem>
      </Nav>
    </Container>

  );
}
import React, { useEffect, useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { IRootState } from '../../../app-front/shared/reducers';
import { RouteComponentProps } from 'react-router-dom';
import { getSearchEntities, getEntities } from '../../shared/reducers/livraison2.reducer';
import { Table } from 'reactstrap';
import { red } from '@material-ui/core/colors';
const color = {
  color: 'red'

};
const getColor = livraison => {
  let classes = "badge m-3 badge-";
  classes += livraison.categorieClient === 'Gold' ? "danger" : "primary";
  return classes;
}
const isAdminMvntType = livraison => {
  if (livraison.categorieClient === 'Gold') {
    return (
      <React.Fragment>
        <h1 className={getColor(livraison)}><h6>STATUS Gold</h6></h1>
        <b> Si vous êtes  dans la zone <a style={{ color: "red" }}>{livraison.zoneNom}</a> : </b>
        <h5 style={{ color: "red" }}>Des frais de livraison de {livraison.frais} DT dans un délai de  {livraison.date} jours </h5>
        <p>Vous bénéficiez de frais de livraison réduits pour chacune de vos
                  commandes avec une somme minimale de  {livraison.valeurMin} DT et une somme maximale
                   de {livraison.valeurMax} DT pour que la réception de vos courses s’effectue dans les
                   meilleures conditions</p>

      </React.Fragment>
    );
  } else if (livraison.categorieClient === 'Silver') {
    return (
      <React.Fragment>
        <h1 className={getColor(livraison)}><h6>STATUS Silver</h6></h1>
        <b> Si vous êtes  dans la zone <a style={{ color: "blue" }}>{livraison.zoneNom}</a> : </b>
        <h5 style={{ color: "blue" }}>Des frais de livraison de {livraison.frais} DT dans un délai de  {livraison.date} jours </h5>
        <p>Vous bénéficiez de frais de livraison réduits pour chacune de vos
                  commandes avec une somme minimale de  {livraison.valeurMin} DT et une somme maximale
                   de {livraison.valeurMax} DT pour que la réception de vos courses s’effectue dans les
                   meilleures conditions</p>     </React.Fragment>
    );
  }
  else {
    <React.Fragment>
      <h1 className={getColor(livraison)}><h6>STATUS Platinuim</h6></h1>
      <b> Si vous êtes  dans la zone <a style={{ color: "blue" }}>{livraison.zoneNom}</a> : </b>
      <h5 style={{ color: "blue" }}>Des frais de livraison de {livraison.frais} DT dans un délai de  {livraison.date} jours </h5>
      <p>Vous bénéficiez de frais de livraison réduits pour chacune de vos
                  commandes avec une somme minimale de  {livraison.valeurMin} DT et une somme maximale
                   de {livraison.valeurMax} DT pour que la réception de vos courses s’effectue dans les
                   meilleures conditions</p>     </React.Fragment>
  }
};

export class CGUModalFidelite extends React.Component<any> {
  handleCloseFid: any;
  showModalFid: any;

  //  const LivraisonModal = (props) => {
  //   const [search, setSearch] = useState('');

  // useEffect(() => {
  //   props.getEntities();
  // }, []);



  componentDidMount() {
    this.props.getEntities();
  }







  render() {
    const { handleCloseFid, showModalFid, listLivrais, match, loading } = this.props;

    return (
      <Modal isOpen={showModalFid} toggle={handleCloseFid} backdrop="static" id="login-page" autoFocus={false}>
        <ModalHeader id="login-title" toggle={handleCloseFid}>
          Avantage de fidélité
      </ModalHeader>
        <ModalBody>




          {listLivrais && listLivrais.length > 0 ? (



            // listLivrais.map((livraison, i) => (

            //   <a key={`entity-${i}`}>
            //     {livraison.categorieClient === 'Gold' ?

            //       livraison.map(function (l, j) {
            //         return (
            //           <a key={`entity-${j}`}>
            //             <h4>ZONE : {l.zoneNom} </h4>
            //           </a>
            //         )
            //       })

            //       : "pas de gold"}


            listLivrais.map((livraison, i) => (

              <a key={`entity-${i}`}>

                {isAdminMvntType(livraison)}




              </a>
            ))












          ) : (

              <ModalBody>
                <h2>STATUT Platinium2</h2>
                <p>
                  <h4>Avec le statut Gold, vous bénéficiez des livraisons gratuites. </h4>
                    Un paiement de votre commande à la livraison , Vous recevrez alors votre commande à votre domicile et vous payez à la livraison, en espèces, par chèque ou par Carte bancaire, directement à notre chauffeur habilité à réceptionner votre règlement.
                              </p>
                <p>
                  <h4> Un paiement de votre commande à la livraison </h4>
                    Vous recevrez alors votre commande à votre domicile et vous payez à la livraison, en espèces, par chèque ou par Carte bancaire, directement à notre chauffeur habilité à réceptionner votre règlement.
                              </p>




                <h2>STATUT Gold</h2>
                <p>
                  <h4> Des frais de livraison réduits </h4>
                    Vous bénéficiez de frais de livraison réduits pour chacune de vos commandes, pour que la réception de vos courses s’effectue dans les meilleures conditions, nous apportons un soin particulier à la préparation de vos commandes livrées directement chez vous par nos chauffeurs dans un délai optimisé grâce à un parc de véhicules dédiés, sans intermédiaire.
                </p>
                <p>
                  <h4> Un paiement de votre commande à la livraison </h4>
                    Vous recevrez alors votre commande à votre domicile et vous payez à la livraison, en espèces, par chèque ou par Carte bancaire, directement à notre chauffeur habilité à réceptionner votre règlement.
                              </p>

                <h2>STATUT Silver</h2>
                <p>
                  <h4> Des frais de livraison réduits </h4>
                    Vous bénéficiez de frais de livraison réduits pour chacune de vos commandes, pour que la réception de vos courses s’effectue dans les meilleures conditions, nous apportons un soin particulier à la préparation de vos commandes livrées directement chez vous par nos chauffeurs dans un délai optimisé grâce à un parc de véhicules dédiés, sans intermédiaire.
                </p>
                <p>
                  <h4> Un paiement de votre commande à la livraison </h4>
                    Vous recevrez alors votre commande à votre domicile et vous payez à la livraison, en espèces, par chèque ou par Carte bancaire, directement à notre chauffeur habilité à réceptionner votre règlement.
                              </p>
              </ModalBody>
            )
          }
        </ModalBody>
      </Modal >





    );

  };
}

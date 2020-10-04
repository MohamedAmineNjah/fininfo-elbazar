import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';

export interface ICGUModalProps {
  showModal: boolean;

  handleClose: Function;
}

class CGUModal extends React.Component<ICGUModalProps> {
  render() {
    const { handleClose } = this.props;

    return (
      <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <ModalHeader id="login-title" toggle={handleClose}>
          Conditions générales
        </ModalHeader>
        <ModalBody>
          <Row>
            <h4>Comment nos services sont financés</h4>
            <p>
              Au lieu de payer pour l’utilisation de Facebook et des autres produits et services que nous offrons, en utilisant les Produits
              Facebook inclus dans les présentes Conditions, vous acceptez que nous vous montrions les publicités payées par les entreprises
              et organisations pour que nous les promouvions sur les Produits des entités Facebook et en dehors. Nous utilisons vos données
              personnelles, telles que les informations concernant votre activité et vos intérêts, afin de vous montrer des publicités plus
              pertinentes pour vous. La protection de la vie privée est au cœur de la conception de notre système publicitaire. Cela
              signifie que nous pouvons vous montrer des publicités pertinentes et utiles sans informer les annonceurs sur votre identité.
              Nous ne vendons pas vos données personnelles. Nous autorisons les annonceurs à nous donner des informations, telles que leur
              objectif commercial et le type d’audience à qui ils souhaitent montrer leurs publicités (par exemple, les personnes âgées de
              18 à 35 ans qui aiment le vélo). Puis, nous montrons leur publicité aux personnes susceptibles d’être intéressées. De plus,
              nous envoyons aux annonceurs des rapports sur les performances de leurs publicités pour les aider à comprendre comment les
              gens interagissent avec leur contenu sur Facebook et en dehors. Par exemple, nous communiquerons aux annonceurs des
              informations générales concernant la démographie et les centres d’intérêt (par exemple, une publicité a été vue par une femme
              entre 25 et 34 ans qui vit à Madrid et qui aime l’informatique) pour les aider à mieux comprendre leur audience. Nous ne
              partageons aucune information qui vous identifie directement (des informations comme votre nom ou adresse e-mail qui, en
              elles-mêmes, peuvent servir à vous contacter ou à vous identifier) sans avoir obtenu votre autorisation spécifique. Découvrez
              comment fonctionnent les publicités Facebook ici. Nous collectons et utilisons vos données personnelles afin de vous fournir
              les services décrits ci-dessus. Pour en savoir plus sur la façon dont nous collectons et utilisons vos données, consultez
              notre Politique d’utilisation des données. Vous pouvez contrôler les types de publicités et d’annonceurs que vous voyez ainsi
              que les types d’informations que nous utilisons pour définir les publicités que nous vous montrons. En savoir plus.
            </p>
          </Row>
        </ModalBody>
      </Modal>
    );
  }
}

export default CGUModal;

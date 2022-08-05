import React, { useState, useEffect } from "react";
import { findPets } from "../../services/PetServices";
import styles from "./Pets.module.scss";

export const Pets = () => {
    const [pets, setPets] = useState([]);

    useEffect(() => {
    findPets()
            .then(({data}) => {
            setPets(data);
            });
    }, []);
  return (
    <>
        { pets.map(pet => 
        <div className={styles.pets}>

          <div className={styles.elements}>
             
             
             </div>
            <div className={styles.elements}>
              <div >ID</div> 
              <div>{pet.id}</div>
              </div>
              <div className={styles.elements}>
              <div >ISIN</div> 
              <div>{pet.isin}</div>
              </div>

              <div className={styles.elements}>
              <div>CUSIP:</div>
            <div> {pet.cusip}</div>
              </div>

              <div className={styles.elements}>
                <div>ISSUER:</div>
              <div> {pet.issuer}</div>
                </div>

                <div className={styles.elements}>
                  <div>MATUARITY: </div>
                <div>{pet.maturitydate.slice(0,10)}</div>
                </div>


                <div className={styles.elements}>
                  <div>COUPON</div>
                <div>{pet.coupon}</div>
                </div>

                <div className={styles.elements}>
                <div>TYPE:</div>
                <div> {pet.type}</div>
                
                </div>

                <div className={styles.elements}>
                <div>FACEVALUE: </div>
                <div>{pet.facevalue}</div>
                </div>
                <div className={styles.elements}>
                  <div>STATUS:</div>
                <div> {pet.status}</div>
                </div>

           
            
            
          
           
            
            

        </div>) 
        }
    </>
  )
};

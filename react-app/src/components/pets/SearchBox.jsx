import React, { useState, useEffect } from "react";
import { findPets } from "../../services/PetServices";
import styles from "./Pets.module.scss";

export const SearchBox = () => {
    
  return (
    <form
    id="addSecurity"
    action="http://localhost:8080/api/v1/dogs"
    method="post">
    <h2>
        Add securities
    </h2>
    <label>
        <span class="text">ID:</span>
        <input type="number" name="id"/><br/>
    </label>
    <br/>
    <label>
        <span class="text">Name</span>
        <input type="text" name="name"/><br/>
    </label><br/>
    <label>
        <span class="text">Age</span>
        <input type="number" name="age"/><br/>
    </label><br/>
    <div class="align-right">
        <button type="submit">Submit</button>
    </div>
</form>
  )
};

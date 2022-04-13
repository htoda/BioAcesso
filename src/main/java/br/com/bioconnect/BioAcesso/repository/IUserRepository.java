package br.com.bioconnect.BioAcesso.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bioconnect.BioAcesso.model.User;

public interface IUserRepository extends JpaRepository<User, BigInteger> {

}

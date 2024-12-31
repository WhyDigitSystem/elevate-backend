package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.CityDTO;
import com.ebooks.elevate.dto.ClientDTO;
import com.ebooks.elevate.dto.CompanyDTO;
import com.ebooks.elevate.dto.CountryDTO;
import com.ebooks.elevate.dto.CurrencyDTO;
import com.ebooks.elevate.dto.FinScreenDTO;
import com.ebooks.elevate.dto.FinancialYearDTO;
import com.ebooks.elevate.dto.RegionDTO;
import com.ebooks.elevate.dto.ScreenNamesDTO;
import com.ebooks.elevate.dto.StateDTO;
import com.ebooks.elevate.entity.CityVO;
import com.ebooks.elevate.entity.ClientVO;
import com.ebooks.elevate.entity.CompanyVO;
import com.ebooks.elevate.entity.CountryVO;
import com.ebooks.elevate.entity.CurrencyVO;
import com.ebooks.elevate.entity.FinScreenVO;
import com.ebooks.elevate.entity.FinancialYearVO;
import com.ebooks.elevate.entity.RegionVO;
import com.ebooks.elevate.entity.ScreenNamesVO;
import com.ebooks.elevate.entity.StateVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);

	Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException; // Return the created
																								// entity

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates(Long orgid);

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(Long orgid, String country);

	Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException;

	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities(Long orgid);

	List<CityVO> getAllCitiesByState(Long orgid, String state);

	Optional<CityVO> getCityById(Long cityid);

	Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException;

	void deleteCity(Long cityid);

	// Currency

	List<CurrencyVO> getAllCurrency(Long orgid);

	Optional<CurrencyVO> getCurrencyById(Long currencyid);

	Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException;

	void deleteCurrency(Long currencyid);

	// region

	List<RegionVO> getAllRegios();

	List<RegionVO> getAllRegionsByOrgId(Long orgId);

	Optional<RegionVO> getRegionById(Long regionid);

	Map<String, Object> createUpdateRegion(RegionDTO regionDTO) throws ApplicationException;

	void deleteRegion(Long regionid);

	// Company

	List<CompanyVO> getAllCompany();

	List<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyDTO companyDTO) throws Exception;

	CompanyVO updateCompany(CompanyDTO companyDTO) throws ApplicationException;

	void deleteCompany(Long companyid);

	// FINANCIAL YEAR

	Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException;

	List<FinancialYearVO> getAllActiveFInYear(Long orgId);

	List<FinancialYearVO> getAllFInYearByOrgId(Long orgId);

	Optional<FinancialYearVO> getAllFInYearById(Long id);

//	FinScreen
	List<ScreenNamesVO> getFinScreenById(Long id);

	ScreenNamesVO updateCreateFinScreen(@Valid FinScreenDTO finScreenDTO) throws ApplicationException;

	List<Map<String, Object>> getAllScreenCode(Long orgId);

	// Screen Names
	Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException;

	List<ScreenNamesVO> getAllScreenNames();

	ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException;

	List<Map<String, Object>> getAllCurrencyForExRate(Long orgId);
	
	//CLIENT

	Map<String, Object> createUpdateClient(ClientDTO clientDTO) throws ApplicationException;

	List<ClientVO> getAllClients(Long orgId);

	Optional<ClientVO> getClientById(Long id);

	

}

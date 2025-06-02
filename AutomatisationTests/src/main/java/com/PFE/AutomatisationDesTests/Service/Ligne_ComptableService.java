package com.PFE.AutomatisationDesTests.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.PFE.AutomatisationDesTests.Entity.Activite;
import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Entity.Produit;
import com.PFE.AutomatisationDesTests.Entity.StatutLigne;
import com.PFE.AutomatisationDesTests.Entity.StatutTest;
import com.PFE.AutomatisationDesTests.Entity.TestEcriture;
import com.PFE.AutomatisationDesTests.Entity.TypeEcritureComptable;
import com.PFE.AutomatisationDesTests.Entity.TypeTest;
import com.PFE.AutomatisationDesTests.Entity.Utilisateur;
import com.PFE.AutomatisationDesTests.Repository.Ligne_ComptableRepositry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Transactional
@Service
public class Ligne_ComptableService implements Ligne_ComptableIService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private Ligne_ComptableRepositry ligneRepository;
	@Autowired
	private UtilisateurService userRepository;
	@Autowired
	private ProduitService prodService;
	@Autowired
	private TestEcritureService testService;
	@Autowired
	private HistoriqueService historiqueService;

	@Override
	@Transactional
	public String upload(MultipartFile file) {
		// TODO Auto-generated method stub
		// Check if file is empty
		if (file.isEmpty()) {
			return "No file selected";
		}

		// Check file type (Excel)
		String filename = file.getOriginalFilename();
		if (!(filename.endsWith(".xls") || filename.endsWith(".xlsx"))) {
			return "Fichier non valide. Veuillez sélectionner un fichier au format Excel.";
		}

		try {
			// Check if file is a valid Excel file
			InputStream inputStream = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(inputStream);

			if (ligneRepository.countByFileName(filename) > 0) {
				return "Le ficher déjà importer";
			} else {
				Sheet sheet = workbook.getSheetAt(0);
				DecimalFormat df = new DecimalFormat("0.000000");
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					Ligne_Comptable ligne = new Ligne_Comptable();
					ligne.setTab(row.getCell(0) == null ? "null" : row.getCell(0).getStringCellValue());
					ligne.setRecId(row.getCell(1) == null ? "null"
							: row.getCell(1).getCellType() == CellType.STRING ? row.getCell(1).getStringCellValue()
									: String.valueOf(df.format(row.getCell(1).getNumericCellValue())));

					ligne.setOurReference(row.getCell(2) == null ? "null"
							: row.getCell(2).getCellType() == CellType.STRING ? row.getCell(2).getStringCellValue()
									: String.valueOf((int) row.getCell(2).getNumericCellValue()));
					ligne.setTransReference(row.getCell(3) == null ? "null"
							: row.getCell(3).getCellType() == CellType.STRING ? row.getCell(3).getStringCellValue()
									: String.valueOf((int) row.getCell(3).getNumericCellValue()));
					ligne.setCodeContrat(row.getCell(4) == null ? "null"
							: row.getCell(4).getCellType() == CellType.STRING ? row.getCell(4).getStringCellValue()
									: String.valueOf(row.getCell(4).getNumericCellValue()));
					ligne.setCustomerId(row.getCell(5) == null ? "null"
							: row.getCell(5).getCellType() == CellType.STRING ? row.getCell(5).getStringCellValue()
									: String.valueOf((int) row.getCell(5).getNumericCellValue()));
					ligne.setAccountNumber(row.getCell(6) == null ? "null"
							: row.getCell(6).getCellType() == CellType.STRING ? row.getCell(6).getStringCellValue()
									: String.valueOf(BigDecimal.valueOf(row.getCell(6).getNumericCellValue())));

					ligne.setAccountOfficer(row.getCell(7) == null ? "null"
							: row.getCell(7).getCellType() == CellType.STRING ? row.getCell(7).getStringCellValue()
									: String.valueOf((int) row.getCell(7).getNumericCellValue()));

					if (row.getCell(8) == null) {
						ligne.setProductCateg(0);
					} else if (row.getCell(8).getCellType() == CellType.STRING) {
						ligne.setProductCateg(Integer.valueOf(row.getCell(8).getStringCellValue()));
					} else {
						ligne.setProductCateg((int) row.getCell(8).getNumericCellValue());
					}
					ligne.setTransactionCode(row.getCell(9) == null ? "null"
							: row.getCell(9).getCellType() == CellType.STRING ? row.getCell(9).getStringCellValue()
									: String.valueOf((int) row.getCell(9).getNumericCellValue()));

					ligne.setNarrative(row.getCell(10) == null ? "null" : row.getCell(10).getStringCellValue());

					ligne.setNarrative_6(row.getCell(11) == null ? "null" : row.getCell(11).getStringCellValue());

					ligne.setExchangeRate(row.getCell(12) == null ? "null"
							: row.getCell(12).getCellType() == CellType.STRING ? row.getCell(12).getStringCellValue()
									: String.valueOf(row.getCell(12).getNumericCellValue()));

					if (row.getCell(13) == null) {
						ligne.setAmountFcy(0.0);

					} else if (row.getCell(13).getCellType() == CellType.STRING) {
						ligne.setAmountFcy(Double.valueOf(row.getCell(13).getStringCellValue()));
					} else {
						ligne.setAmountFcy(row.getCell(13).getNumericCellValue());
					}
					if (row.getCell(14) == null) {
						ligne.setAmountLcy(0.0);
					} else if (row.getCell(14).getCellType() == CellType.STRING) {
						double value = Double.valueOf(row.getCell(14).getStringCellValue());
						BigDecimal bd = new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
						ligne.setAmountLcy(bd.doubleValue());
					} else {
						double value = row.getCell(14).getNumericCellValue();
						BigDecimal bd = new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
						ligne.setAmountLcy(bd.doubleValue());
					}
					ligne.setCurrency(row.getCell(15) == null ? "null"
							: row.getCell(15).getCellType() == CellType.STRING ? row.getCell(15).getStringCellValue()
									: String.valueOf(row.getCell(15).getNumericCellValue()));

					if (row.getCell(16) != null) {
						String value = "";
						if (row.getCell(16).getCellType() == CellType.STRING) {
							value = row.getCell(16).getStringCellValue();
						} else {
							value = String.valueOf((int) row.getCell(16).getNumericCellValue());
						}
						if (value.length() < 8) {
							ligne.setBookingDate(null);
						} else {
							try {

								String dateStr = value;
								int year = Integer.parseInt(dateStr.substring(0, 4));
								int month = Integer.parseInt(dateStr.substring(4, 6));
								int day = Integer.parseInt(dateStr.substring(6, 8));
								ligne.setBookingDate(LocalDate.of(year, month, day));
							} catch (Exception e) {
								ligne.setBookingDate(null);
							}
						}
					} else {
						ligne.setBookingDate(null);

					}

					if (row.getCell(17) != null) {
						String value = "";
						if (row.getCell(17).getCellType() == CellType.STRING) {
							value = row.getCell(17).getStringCellValue();
						} else {
							value = String.valueOf((int) row.getCell(17).getNumericCellValue());
						}
						if (value.length() < 8) {
							ligne.setValueDate(null);
						} else {
							try {

								String dateStr = value;
								int year = Integer.parseInt(dateStr.substring(0, 4));
								int month = Integer.parseInt(dateStr.substring(4, 6));
								int day = Integer.parseInt(dateStr.substring(6, 8));
								ligne.setValueDate(LocalDate.of(year, month, day));
							} catch (Exception e) {
								ligne.setValueDate(null);
							}
						}
					} else {
						ligne.setValueDate(null);

					}

					ligne.setDealerDesk(row.getCell(18) == null ? "null"
							: row.getCell(18).getCellType() == CellType.STRING ? row.getCell(18).getStringCellValue()
									: String.valueOf(row.getCell(18).getNumericCellValue()));
					ligne.setSystemId(row.getCell(19) == null ? "null"
							: row.getCell(19).getCellType() == CellType.STRING ? row.getCell(19).getStringCellValue()
									: String.valueOf(row.getCell(19).getNumericCellValue()));

					if (row.getCell(20) != null) {
						String value = "";
						if (row.getCell(20).getCellType() == CellType.STRING) {
							value = row.getCell(20).getStringCellValue();

						} else {
							value = String.valueOf(row.getCell(20).getNumericCellValue() / 1000);

						}
						if (value.length() < 4) {
							ligne.setDate(null);
						} else {

							try {
								String dateStr = value;

								int year = Integer.parseInt(dateStr.substring(0, 2));
								int month = Integer.parseInt(dateStr.substring(2, 4));
								int day = Integer.parseInt(dateStr.substring(4, 6));

								ligne.setDate(LocalDate.of(2000 + year, month, day));
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
					} else {
						ligne.setDate(null);

					}

					ligne.setToChar(row.getCell(21) == null ? "null" : row.getCell(21).getStringCellValue());
					ligne.setConsolKey(row.getCell(22) == null ? "null" : row.getCell(22).getStringCellValue());

					ligne.setFileName(filename);

					ligneRepository.save(ligne);

				}

				return "Le fichier a été importé avec succès";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "Error importation fichier";
		} catch (Exception e) {
			e.printStackTrace();
			return "Fichier non valide. Veuillez sélectionner un fichier au format Excel";
		}
	}

	@Override
	@Transactional
	public List<Ligne_Comptable> searchLignes(List<Object> criteria) {
		StringBuilder queryString = new StringBuilder("SELECT l FROM Ligne_Comptable l WHERE 1=1 "); // Base query
		LocalDate date = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (Object object : criteria) {
			Map<String, Object> criterion = (Map<String, Object>) object;

			if (criterion.get("value") != "") {

				switch ((String) criterion.get("operator")) {
				case "Equal":
					if ((String) criterion.get("type") == "date") {
						date = LocalDate.parse(criterion.get("value").toString(), formatter);
						queryString.append("AND l." + criterion.get("name") + " = :date ");
					} else {
						queryString.append(
								"AND l." + criterion.get("name") + " = '" + criterion.get("value").toString() + "'");
					}
					break;
				case "NotEqual":
					queryString.append(
							"AND l." + criterion.get("name") + " <> '" + criterion.get("value").toString() + " '");
					break;
				case "Contains":
					queryString.append(
							"AND l." + criterion.get("name") + " LIKE '%" + criterion.get("value").toString() + "%'");
					break;
				case "NotContains":
					queryString.append("AND l." + criterion.get("name") + " NOT LIKE '%"
							+ criterion.get("value").toString() + "%'");
					break;
				case "Startwith":
					queryString.append(
							"AND l." + criterion.get("name") + " LIKE '" + criterion.get("value").toString() + "%'");
					break;
				case "NotStartwith":
					queryString.append("AND l." + criterion.get("name") + " NOT LIKE '"
							+ criterion.get("value").toString() + "%'");
					break;
				case "Endwith":
					queryString.append(
							"AND l." + criterion.get("name") + " LIKE '%" + criterion.get("value").toString() + "'");
					break;
				case "NotEndWith":
					queryString.append("AND l." + criterion.get("name") + " NOT LIKE '%"
							+ criterion.get("value").toString() + "'");
					break;
				case "<": {
					date = LocalDate.parse(criterion.get("value").toString(), formatter);
					queryString.append("AND l." + criterion.get("name") + " < :date ");
					break;
				}
				case "<=": {
					date = LocalDate.parse(criterion.get("value").toString(), formatter);
					queryString.append("AND l." + criterion.get("name") + " <= :date ");
					break;
				}
				case ">": {
					date = LocalDate.parse(criterion.get("value").toString(), formatter);
					queryString.append("AND l." + criterion.get("name") + " > :date ");
					break;
				}
				case ">=": {
					date = LocalDate.parse(criterion.get("value").toString(), formatter);
					queryString.append("AND l." + criterion.get("name") + " >= :date ");
					break;
				}
				default:
					throw new IllegalArgumentException("Invalid operator: " + (String) criterion.get("operator"));
				}
			}
		}

		queryString.append("order by l.codeContrat, abs(l.amountLcy) desc");
		TypedQuery<Ligne_Comptable> query = entityManager.createQuery(queryString.toString(), Ligne_Comptable.class);

		if (date != null) {
			query.setParameter("date", date);
		}

		return query.getResultList();
	}

	@Override
	@Transactional
	public String test(String code, String email, Long prodId, boolean test) {
		TestEcriture t = null;
		List<String> names = ligneRepository.findTrasnctionByCode_Contrat(code);
		if (names.size() > 0) {

			List<Ligne_Comptable> lignes = ligneRepository.findByCode_Contrat(code);

			Utilisateur u = userRepository.getUtilisateurbyEmail(email);
			Produit p = prodService.getProduitById(prodId);
			int c = p.getCategorie();
			long size = ligneRepository.countByCodeContratAndProductCateg(code, c);
			if (size > 2) {
				t = new TestEcriture(code, TypeTest.EnCour, StatutTest.NON_VALIDE);
				double creditAmount = ligneRepository.getCreditAmount(code);
				t.setUser(u);
				t.setProduit(p);
				String process = typeProcess(names, lignes);
				switch (process) {

				case "les Deux": {
					processInitiation(names, lignes, t, creditAmount, c);
					processReglement(names, lignes, t);
					t.setLignesTester(verification(t, c));
					break;
				}
				case "Reglement": {
					processReglement(names, lignes, t);
					t.setLignesTester(verifReglement(t, c));
					break;
				}
				case "Initiation": {
					processInitiation(names, lignes, t, creditAmount, c);
					t.setLignesTester(verifTransaction(t, c));
					break;
				}

				}

				if (t.getError() != "") {
					t.setStatut(TypeTest.TestNonConcluant);
				} else {
					t.setStatut(TypeTest.TestConcluant);
				}
				testService.ajouterTest(t);
				Historique h = new Historique(t.getUser(), p, p.getGroupeProduit(), code, t.getStatut(),
						Activite.DEROULEMENT);
				historiqueService.ajouterHistorique(h);
				return "Test complete";
			} else {
				if (test) {
					t = new TestEcriture(code, TypeTest.EnCour, StatutTest.NON_VALIDE);
					t.setUser(u);
					t.setProduit(p);
					t.setError("Le code catégorie de produit  est incorrect");
					t.setStatut(TypeTest.TestNonConcluant);
					List<Ligne_Comptable> tester = new ArrayList<>();
					for (Ligne_Comptable l : lignes) {
						l.setStatut(StatutLigne.Incorrect);
						ligneRepository.save(l);
						tester.add(l);
					}
					t.setLignesTester(tester);
					testService.ajouterTest(t);
					Historique h = new Historique(t.getUser(), p, p.getGroupeProduit(), code, t.getStatut(),
							Activite.DEROULEMENT);
					historiqueService.ajouterHistorique(h);
					return "Test complete";
				} else {
					return "Le code catégorie de produit  est incorrect";
				}
			}
		} else {
			return "Le code du contrat est incorrect";
		}

	}

	private String typeProcess(List<String> transaction, List<Ligne_Comptable> lignes) {
		if (verifierConstatationEchenace(transaction, lignes) && verifierInitialisation(transaction, lignes)) {
			return "les Deux";
		} else {
			if (verifierInitialisation(transaction, lignes)) {
				return "Initiation";
			} else {
				return "Reglement";
			}
		}

	}

	private boolean verifierConstatationEchenace(List<String> transaction, List<Ligne_Comptable> lignes) {
		for (String tr : transaction) {
			boolean rep = false;
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getConsolKey().startsWith("PL") && l.getAmountLcy() > 0) {
						if (rep) {
							return true;
						} else {
							rep = true;
						}
					}
					if (l.getConsolKey().endsWith("DUECOMMISSIONECH") && l.getAmountLcy() < 0) {
						if (rep) {
							return true;
						} else {
							rep = true;
						}
					}
				}
			}

		}
		return false;
	}

	private boolean verifierInitialisation(List<String> transaction, List<Ligne_Comptable> lignes) {
		for (String tr : transaction) {
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getTab().contains("RE_SPEC_ENTRY") && l.getConsolKey().endsWith("CURCOMMITMENT")) {
						if (l.getAmountLcy() < 0) {
							return true;
						}

					}
				}
			}

		}
		return false;
	}

	private void processReglement(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		accrual(transaction, lignes, test, test.getContrat());
		accrualSurInteretConventionnel(transaction, lignes, test);
		constatationEchenace(transaction, lignes, test);
		preparationAuPaiement(transaction, lignes, test);
		acc(transaction, lignes, test);
	}

	private void preparationAuPaiement(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			boolean rep = false;
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getConsolKey().endsWith("DUECOMMISSIONECH") && l.getAmountLcy() > 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Preparation au paiement"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}

					}
					if (l.getConsolKey().endsWith("AASUSPENSE") && l.getAmountLcy() < 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Preparation au paiement"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}
					}

				}
			}

		}
		test.setError(test.getError() + "Preparation au paiement");
	}

	private void accrualSurInteretConventionnel(List<String> transaction, List<Ligne_Comptable> lignes,
			TestEcriture test) {
		for (String tr : transaction) {
			boolean rep = false;
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getConsolKey().startsWith("PL") && l.getAmountLcy() < 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Accrual sur interet conventionnel"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}

					}
					if (l.getConsolKey().endsWith("ACCPRINCIPALINT") && l.getAmountLcy() > 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Accrual sur interet conventionnel"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}
					}

				}
			}

		}
		test.setError(test.getError() + "Accrual sur interet conventionnel");
	}

	private void constatationEchenace(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			boolean rep = false;
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getConsolKey().startsWith("PL") && l.getAmountLcy() > 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Constatation de la tombé d'échénace"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}
					}
					if (l.getConsolKey().endsWith("DUECOMMISSIONECH") && l.getAmountLcy() < 0) {
						if (rep) {
							test.getType().add(new TypeEcritureComptable(tr, "Constatation de la tombé d'échénace"));
							transaction.remove(tr);
							return;
						} else {
							rep = true;
						}
					}
				}
			}

		}
		test.setError(test.getError() + "Constatation de la tombé d'échénace");
	}

	private void acc(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {

					if (l.getConsolKey().endsWith("ACCPRINCIPALINT") && l.getAmountLcy() < 0) {

						test.getType().add(new TypeEcritureComptable(tr, "acc"));
						transaction.remove(tr);
						return;
					}

				}
			}

		}
		test.setError(test.getError() + " Aucun Acc ");
	}

	public void accrual(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test, String code) {

		for (String trs : transaction) {
			for (Ligne_Comptable lig : lignes) {
				if (lig.getTransReference().equals(trs)) {
					if (lig.getTransReference().startsWith(code)) {
						test.getType().add(new TypeEcritureComptable(trs, "accrual"));
						transaction.remove(trs);

						return;
					}
				}
			}

		}
		test.setError(test.getError() + " Acune Accrual");

	}

	private void processInitiation(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test,
			double amount, int c) {
		initialisation(transaction, lignes, test);
		PreparationDeblocage(transaction, lignes, test);
		Deblocage(transaction, lignes, test, amount);
		DeblocageBank(transaction, lignes, test, amount);
		constatation(transaction, lignes, test);
		payementCommissionClient(transaction, lignes, test, c, amount);

	}

	private void initialisation(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getTab().contains("RE_SPEC_ENTRY") && l.getConsolKey().endsWith("CURCOMMITMENT")) {
						if (l.getAmountLcy() < 0) {
							test.getType().add(new TypeEcritureComptable(tr, "initialisation"));
							transaction.remove(tr);
							return;
						}

					}
				}
			}

		}
		test.setError(test.getError() + " Aucun initialisation du contrat ");
	}

	private void constatation(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {

					if (l.getTab().contains("RE_SPEC_ENTRY") && l.getConsolKey().endsWith("CURCOMMITMENT")) {
						if (l.getAmountLcy() > 0) {
							test.getType().add(new TypeEcritureComptable(tr, "constatation"));
							transaction.remove(tr);
							return;
						}
					}
				}
			}

		}
		test.setError(test.getError() + " Aucun Constatation ");
	}

	private void PreparationDeblocage(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test) {
		for (String tr : transaction) {
			for (Ligne_Comptable l : lignes) {
				if (l.getTransReference().equals(tr)) {
					if (l.getTab().contains("RE_SPEC_ENTRY") && l.getConsolKey().endsWith("AVLACCOUNTBL")) {
						if (l.getAmountLcy() < 0) {
							test.getType().add(new TypeEcritureComptable(tr, "Preparation pour Deblocage"));
							transaction.remove(tr);

							return;
						}

					}
				}
			}

		}
		test.setError(test.getError() + " Aucun Prépration pour Deblocage ");
	}

	private void Deblocage(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test, double amount) {

		for (String trs : transaction) {
			for (Ligne_Comptable lig : lignes) {
				if (lig.getTransReference().equals(trs)) {

					if (amount == lig.getAmountLcy() && lig.getProductCateg() > 1000 && lig.getProductCateg() < 2000) {

						test.getType().add(new TypeEcritureComptable(trs, "deblocage Couté Client"));
						transaction.remove(trs);

						return;
					}
				}
			}

		}
		test.setError(test.getError() + " Aucun Deblocage Couté client ");
	}

	private void DeblocageBank(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test,
			double amount) {

		for (String trs : transaction) {
			for (Ligne_Comptable lig : lignes) {
				if (lig.getTransReference().equals(trs)) {
					if (amount == lig.getAmountLcy() && lig.getConsolKey().endsWith("AASUSPENSE")) {
						test.getType().add(new TypeEcritureComptable(trs, "deblocage Couté Bank"));
						transaction.remove(trs);

						return;
					}
				}
			}

		}
		test.setError(test.getError() + " Aucun Deblocage Couté Bank ");
	}

	public void payementCommissionClient(List<String> transaction, List<Ligne_Comptable> lignes, TestEcriture test,
			int c, double amount) {

		for (String trs : transaction) {
			for (Ligne_Comptable lig : lignes) {
				if (lig.getTransReference().equals(trs)) {
					if (amount != lig.getAmountLcy() && lig.getSystemId().equals("AA") && lig.getCustomerId() != null
							&& lig.getProductCateg() > 1000 && lig.getProductCateg() < 2000) {
						test.getType().add(new TypeEcritureComptable(trs, "payementcommissionclient"));
						transaction.remove(trs);

						return;
					}
				}
			}

		}
		test.setError(test.getError() + " La commission non payée pour client");

	}
	public List<Ligne_Comptable> verification(TestEcriture t, int c){
		List<Ligne_Comptable> tester = new ArrayList<>();
		tester.addAll(verifTransaction(t,c));
		tester.addAll(verifReglement(t,c));
		return tester;
	}
	public List<Ligne_Comptable> verifTransaction(TestEcriture t, int c) {
		List<Ligne_Comptable> tester = new ArrayList<>();
		for (TypeEcritureComptable transaction : t.getType()) {
			List<Ligne_Comptable> lignes = new ArrayList<Ligne_Comptable>();
			String type = transaction.getType();
			lignes = ligneRepository.findByTransReference(transaction.getTransaction());
			if (lignes.size() % 2 != 0) {
				t.setError(t.getError() + "L'ecrutiure comptable pour le transaction référence :  "
						+ transaction.getTransaction() + " n'est pas correct \n");

			}

			BigDecimal x = BigDecimal.ZERO;

			for (Ligne_Comptable l : lignes) {
				boolean rep = true;

				BigDecimal amount = BigDecimal.valueOf(l.getAmountLcy()).setScale(3, RoundingMode.HALF_UP);

				x = x.add(amount);
				switch (type) {
				case "initialisation": {
					if (!l.getConsolKey().contains("CURCOMMITMENT") && !l.getConsolKey().contains("TOTCOMMITMENT")) {
						t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getProductCateg() != c) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (!l.getTransactionCode().contains("NEW")) {
						t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					break;
				}
				case "constatation": {
					if (!l.getConsolKey().contains("CURCOMMITMENT") && !l.getConsolKey().contains("AVLACCOUNT")) {
						t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getProductCateg() != c) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getConsolKey().contains("CURCOMMITMENT")) {
						if (!l.getTransactionCode().contains("NEW")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					} else {
						if (!l.getTransactionCode().contains("PAY")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					}
					break;
				}
				case "Preparation pour Deblocage": {
					if (l.getProductCateg() != c) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (!l.getConsolKey().contains("AVLACCOUNT")) {
						t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (!l.getTransactionCode().contains("PAY")) {
						t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					break;
				}
				case "deblocage Couté Client": {
					if (l.getAmountLcy() > 0) {
						if (!l.getTransactionCode().contains("860")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						if (l.getProductCateg() > 1999 || l.getProductCateg() < 1001) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					} else {
						if (!l.getTransactionCode().contains("SUS")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						if (l.getProductCateg() != c) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					}
					break;
				}
				case "deblocage Couté Bank": {
					if (l.getAmountLcy() > 0) {
						if (!l.getTransactionCode().contains("CNW")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						if (l.getProductCateg() != c) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					} else {
						if (!l.getTransactionCode().contains("861")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						if (l.getProductCateg() != c) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					}
					break;
				}
				case "payementcommissionclient": {

					if (l.getAmountLcy() < 0) {
						if (!l.getTransactionCode().contains("861")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (l.getProductCateg() > 1999 || l.getProductCateg() < 1001) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
					} else {
						if (!l.getConsolKey().endsWith("AASUSPENSE")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getTransactionCode().contains("SUS")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (l.getProductCateg() != c) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}

					}

					break;
				}
				}

				if (rep) {
					l.setStatut(StatutLigne.Correct);
					ligneRepository.save(l);
					tester.add(l);
				} else {
					l.setStatut(StatutLigne.Incorrect);
					ligneRepository.save(l);
					tester.add(l);
				}

			}

			if (x.compareTo(BigDecimal.ZERO) != 0) {
				t.setError(t.getError() + "le partie double n'est pas respecter " + x.floatValue() + " \n");

			}

		}
		return tester;

	}

	public List<Ligne_Comptable> verifReglement(TestEcriture t, int c) {
		List<Ligne_Comptable> tester = new ArrayList<>();
		for (TypeEcritureComptable transaction : t.getType()) {
			List<Ligne_Comptable> lignes = new ArrayList<Ligne_Comptable>();
			String type = transaction.getType();
			lignes = ligneRepository.findByTransReference(transaction.getTransaction());
			if (lignes.size() % 2 != 0) {
				t.setError(t.getError() + "L'ecrutiure comptable pour le transaction référence :  "
						+ transaction.getTransaction() + " n'est pas correct \n");

			}

			BigDecimal x = BigDecimal.ZERO;

			for (Ligne_Comptable l : lignes) {
				boolean rep = true;

				BigDecimal amount = BigDecimal.valueOf(l.getAmountLcy()).setScale(3, RoundingMode.HALF_UP);
				
				x = x.add(amount);
				switch (type) {
				case "Preparation au paiement": {
					if (l.getProductCateg() != c ) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getAmountLcy() > 0) {
						if (l.getConsolKey().endsWith("DUECOMMISSIONECH") || l.getConsolKey().endsWith("DUECOMMECHINT")
								|| l.getConsolKey().endsWith(".")) {
							if (!l.getConsolKey().endsWith(".")) {
								if (!l.getTransactionCode().contains("COM")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							} else {
								if (!l.getTransactionCode().contains("804") &&  !l.getTransactionCode().contains("860")
										&& !l.getTransactionCode().contains("COM") && !l.getTransactionCode().contains("7585")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}

						} else {
							t.setError(t.getError() + "Consol key incorrect  pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
					} else {
						if (!l.getConsolKey().endsWith("AASUSPENSE")) {
							t.setError(t.getError() + "Consol key incorrect  pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getTransactionCode().contains("COM") && !l.getTransactionCode().contains("MVT")
								&& !l.getTransactionCode().contains("CNW")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}

					}
					break;
				}
				case "Accrual sur interet conventionnel": {
					if (l.getProductCateg() != c ) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getAmountLcy() > 0) {

						if (!l.getTransactionCode().contains("MVT")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");
					rep = false;
						}
						if (!l.getConsolKey().endsWith("ACCPRINCIPALINT")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						
					} else {
						if (!l.getTransactionCode().contains("861")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
							+ " de la transaction " + l.getTransReference() + "\n");
					rep = false;
						}
						if (!l.getConsolKey().endsWith(".")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
					}
					break;
				}
				case "Constatation de la tombé d'échénace": {

					if (l.getAmountLcy() < 0) {
						if (l.getProductCateg() != c ) {
							t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");
							rep = false;
						}
						if (l.getConsolKey().endsWith("DUECOMMISSIONECH") || l.getConsolKey().endsWith("DUECOMMECHINT")
								|| l.getConsolKey().endsWith(".") || l.getConsolKey().endsWith("ACCPRINCIPALINT")) {
							if (l.getConsolKey().endsWith("DUECOMMISSIONECH") || l.getConsolKey().endsWith("DUECOMMECHINT")) {
								if (!l.getTransactionCode().contains("COM")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							} else if(l.getConsolKey().endsWith(".")){
								if (!l.getTransactionCode().contains("830") && !l.getTransactionCode().contains("864")
									  && !l.getTransactionCode().contains("7085")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}else {
								if (!l.getTransactionCode().contains("ACC")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}

						} else {
							t.setError(t.getError() + "Consol key incorrect  pour le Record ID  :  " + l.getRecId()
									+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
					} else {
						if (l.getConsolKey().endsWith("ACCPRINCIPALINT") || l.getConsolKey().endsWith(".") && l.getConsolKey().startsWith("AC") || l.getConsolKey().startsWith("PL") || l.getConsolKey().contains("17241")) {
						
							if (l.getConsolKey().endsWith("ACCPRINCIPALINT")) {
								if (l.getProductCateg() != c ) {
									t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
								if (!l.getTransactionCode().contains("MVT")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}else if(l.getConsolKey().contains("17241")) {
								if (!l.getTransactionCode().contains("7585")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
								if (l.getProductCateg() != 17241 ) {
									t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}else {
								if (!l.getTransactionCode().contains("864") &&!l.getTransactionCode().contains("450") && !l.getTransactionCode().contains("2721") && !l.getTransactionCode().contains("2722")) {
									t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								
								}
								if (l.getProductCateg() != c ) {
									t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
											+ " de la transaction " + l.getTransReference() + "\n");
									rep = false;
								}
							}
						}else {
							t.setError(t.getError() + "Consol key incorrect  pour le Record ID  :  " + l.getRecId()
							+ " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						

					}
					break;
				}

				case "acc": {
					if (l.getProductCateg() != c ) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getAmountLcy() < 0) {
						if (!l.getTransactionCode().contains("ACC")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getConsolKey().endsWith("ACCPRINCIPALINT")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
					} else {
						if (!l.getConsolKey().endsWith(".")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getTransactionCode().contains("450")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						

					}

					break;
				}
				case "accrual": {
					if (l.getProductCateg() != c ) {
						t.setError(t.getError() + "Produit category incorrect pour le Record ID  :  " + l.getRecId()
								+ " de la transaction " + l.getTransReference() + "\n");
						rep = false;
					}
					if (l.getAmountLcy() < 0) {
						if (!l.getTransactionCode().contains("ACC")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getConsolKey().endsWith("ACCPRINCIPALINT")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
					} else {
						if (!l.getConsolKey().endsWith(".")) {
							t.setError(t.getError() + "Consol key incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						if (!l.getTransactionCode().contains("450")) {
							t.setError(t.getError() + "TXN incorrect pour le Record ID  :  " + l.getRecId() + " de la transaction " + l.getTransReference() + "\n");

							rep = false;
						}
						

					}

					break;
				}

				}

				if (rep) {
					
					l.setStatut(StatutLigne.Correct);
				} else {
				
					l.setStatut(StatutLigne.Incorrect);
					
				}
				ligneRepository.save(l);
				tester.add(l);
			}

			if (x.compareTo(BigDecimal.ZERO) != 0) {
				t.setError(t.getError() + "le partie double n'est pas respecter " + x.floatValue() + " \n");

			}

		}
		return tester;
	}

	@Override
	public long count() {
		try {
			return ligneRepository.count();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return 0;
		}
	}

}

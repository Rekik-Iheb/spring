package com.PFE.AutomatisationDesTests.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;

public interface Ligne_ComptableIService {

	public String upload(MultipartFile file);
	public String test(String code,String email,Long prodId,boolean test);
	public long count();
	public List<Ligne_Comptable> searchLignes(List<Object> criteria);
}

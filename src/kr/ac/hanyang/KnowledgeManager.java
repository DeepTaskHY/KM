package kr.ac.hanyang;

import java.util.Iterator;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
//import org.apache.jena.query.ResultSetFormatter;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import kr.ac.hanyang.util.Header;
import kr.ac.hanyang.util.RosMessage;
import kr.ac.hanyang.util.SocialContext;
import kr.ac.hanyang.util.Triple;


/**
 * 
 * @author freebeing1
 * @desc 지식 관리자 클래스. 온톨로지에 접근하는 메서드들이 정의되어 있는 클래스.
 */
public class KnowledgeManager {

	// 온톨로지 파일 경로
	protected static final String owlDir = "owl/";
	protected static final String isro_owlfile = owlDir + "isro.owl";	
	protected static final String isro_social_owlfile = owlDir + "isro_social.owl";
	protected static final String isro_medical_owlfile = owlDir + "isro_medical.owl";
	protected static final String isro_map_owlfile = owlDir + "isro_map.owl";
	protected static final String isro_cap_owlfile = owlDir + "isro_cap.owl";
	protected static final String service_owlfile = owlDir + "service.owl";


	// jena OntModel 타입 변수 선언
	// 모든 OntModel의 스펙은 OWL_DL_MEM_TRANS_INF 로 설정
	protected static final OntModel isro_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
	protected static final OntModel isro_social_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
	protected static final OntModel isro_medical_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
	protected static final OntModel isro_map_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
	protected static final OntModel isro_cap_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
	protected static final OntModel service_OntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);

	// iri prefix
	protected static final String PREFIX_xsd = "http://www.w3.org/2001/XMLSchema#";
	protected static final String PREFIX_owl = "http://www.w3.org/2002/07/owl#";
	protected static final String PREFIX_rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	protected static final String PREFIX_rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	protected static final String PREFIX_knowrob = "http://knowrob.org/kb/knowrob.owl#";

	protected static final String PREFIX_isro = "http://robot-arbi.kr/ontologies/isro.owl#";
	protected static final String PREFIX_isro_social = "http://robot-arbi.kr/ontologies/isro_social.owl#";
	protected static final String PREFIX_isro_medical = "http://robot-arbi.kr/ontologies/isro_medical.owl#";
	protected static final String PREFIX_isro_map = "http://robot-arbi.kr/ontologies/isro_map.owl#";
	protected static final String PREFIX_isro_cap = "http://robot-arbi.kr/ontologies/isro_cap.owl#";
	protected static final String PREFIX_service = "http://robot-arbi.kr/ontologies/service.owl#";

	protected static String PREFIX = ""
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"

            + "PREFIX knowrob: <http://knowrob.org/kb/knowrob.owl#> \n"
            + "PREFIX ourk: <http://kb.OntologyUnifiedRobotKnowledge.org#> \n"

            + "PREFIX isro: <http://www.robot-arbi.kr/ontologies/isro.owl#> \n"
            + "PREFIX isro_map: <http://www.robot-arbi.kr/ontologies/isro_map.owl#> \n"
            + "PREFIX isro_cap: <http://www.robot-arbi.kr/ontologies/isro_cap.owl#> \n"
            + "PREFIX isro_social: <http://www.robot-arbi.kr/ontologies/isro_social.owl#> \n"
            + "PREFIX service: <http://www.robot-arbi.kr/ontologies/service.owl#> \n\n";

	
	public KnowledgeManager() {
		isro_OntModel.read(isro_owlfile);
		isro_social_OntModel.read(isro_social_owlfile);
		//		isro_medical_OntModel.read(isro_medical_owlfile);
		isro_map_OntModel.read(isro_map_owlfile);
		isro_cap_OntModel.read(isro_cap_owlfile);

		service_OntModel.read(service_owlfile);

		service_OntModel.addSubModel(isro_OntModel);
		service_OntModel.addSubModel(isro_social_OntModel);
		//		service_OntModel.addSubModel(isro_medical_OntModel);
		service_OntModel.addSubModel(isro_map_OntModel);
		service_OntModel.addSubModel(isro_cap_OntModel);

		System.out.println("Ontology models are now loaded.\n");
	}

//	private static String getNamedSubject(String name) {
//		String subject = queryKM("?s", PREFIX_knowrob+"fullName", name);
//		return subject;
//	}

	private static String query(Triple triple) {

		Triple resultTriple = new Triple();
		String subject = triple.getSubject();
		String predicate = triple.getPredicate();
		String object = triple.getObject();
		
		String s_query;
		String p_query;
		String o_query;
		int mode = 0;

		if(subject.contains("$")) {
			s_query = "?s";
			mode = 1;
		} else {
			s_query = "<"+subject+">"; 
		}

		if(predicate.contains("$")) {
			p_query = "?p";
			mode = 2;
		} else {
			p_query = "<"+predicate+">"; 
		}

		if(object.contains("$")) {
			o_query = "?o";
			mode = 3;
		} else if(object.contains("http://")) {
			o_query = "<" + object +">"; 
		} else {
			o_query = "\"" + object +"\"";
		}


		String queryString = ""
				+ PREFIX

				+ "SELECT \n"

				+ "?s ?p ?o \n"

				+ "WHERE {\n"

				+ s_query + " " + p_query + " " + o_query + " . \n"

				+ "}"
				+ "\n";

		Query sQuery = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(sQuery, service_OntModel);
		ResultSet results = qe.execSelect();
		//ResultSetFormatter.out(System.out, results);

		
		while(results.hasNext()) {

			String s = null;
			String p = null;
			String o = null;

			QuerySolution soln = results.nextSolution();

			try {
				s =soln.getResource("s").getURI();
			} catch (Exception e) {
				try {
					s = soln.getLiteral("s").getString();
				} catch (Exception e2) {
					s = subject;
				}
			}

			try {
				p = soln.getResource("p").getURI();
			} catch (Exception e) {
				try {
					p = soln.getLiteral("p").getString();
				} catch (Exception e2) {
					p = predicate;
				}
			}

			try {
				o = soln.getResource("o").getURI();
			} catch (Exception e) {
				try {
					o = soln.getLiteral("o").getString();
				} catch (Exception e2) {
					o = object;
				}
			}
			
			resultTriple.setTriple(s, p, o);
			
		}
		
		switch(mode) {
		case 1:
			return resultTriple.getSubject();
		case 2:
			return resultTriple.getPredicate();
		case 3:
			return resultTriple.getObject();
		default:
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public RosMessage querySocialContext(RosMessage rosMessage){

		SocialContext socialContext = rosMessage.getSocialContext();
		Header header = rosMessage.getHeader();
		
		RosMessage resultRosMessage = new RosMessage();
		
		// 이름으로부터 subject IRI 먼저 찾기
		String name = socialContext.getName();
		String subject = query(new Triple("$s", PREFIX_knowrob+"fullName", name));
		
		Iterator<String> keys = socialContext.getIsro_social().keySet().iterator();
		
		while(keys.hasNext()) {
			String predicate = keys.next();
			String prefixedPredicate;
			String queryResult = "";
			
			switch(predicate){
			case "gender":
				
				prefixedPredicate = PREFIX_isro_social+predicate;
				queryResult = query(new Triple(subject, prefixedPredicate, "$o"));

				if (queryResult.contains("_Male")) {
					resultRosMessage.getSocialContext().getIsro_social().put(predicate, "남성");
				} else if(queryResult.contains("_Female")) {
					resultRosMessage.getSocialContext().getIsro_social().put(predicate, "여성");
				}		
				break;

			case "appellation":
				
				prefixedPredicate = PREFIX_isro_social+predicate;
				queryResult = query(new Triple(subject, prefixedPredicate, "$o"));
				resultRosMessage.getSocialContext().getIsro_social().put(predicate, queryResult);

				break;

			case "age":
				
				prefixedPredicate = PREFIX_isro_social+predicate;
				queryResult = query(new Triple(subject, prefixedPredicate, "$o"));
				if(queryResult.contains("_AdultAge")) {
					resultRosMessage.getSocialContext().getIsro_social().put(predicate, "성인");
				} else if(queryResult.contains("_YoungAge")) {
					resultRosMessage.getSocialContext().getIsro_social().put(predicate, "어린이");
				} else if(queryResult.contains("_OldAge")) {
					resultRosMessage.getSocialContext().getIsro_social().put(predicate, "노인");
				}
				
				break;

			// 이 아래 3개는 ㅈㄴ 이상함.
			case "visit_History":
				
				prefixedPredicate = PREFIX_service+"visitHistory";
				queryResult = query(new Triple(subject, prefixedPredicate, "$o"));
				resultRosMessage.getSocialContext().getIsro_social().put(predicate, queryResult);
				
				break;

			case "medical_record":
				
				prefixedPredicate = PREFIX_service+"hasMedicalRecord";
				queryResult = query(new Triple(subject, prefixedPredicate, "$o"));
				resultRosMessage.getSocialContext().getIsro_social().put(predicate, queryResult);
				
				break;	

			case "action_sentiment":
				
				queryResult = "Neutral";
				resultRosMessage.getSocialContext().getIsro_social().put(predicate, queryResult);
				
				break;
			}

		}

		resultRosMessage.getHeader().setContent(header.getContent());
		resultRosMessage.getHeader().setSource("knowledge");
		resultRosMessage.getHeader().setTarget(header.getSource());
		resultRosMessage.getHeader().setTimestamp();
		
		resultRosMessage.getSocialContext().setId(socialContext.getId());
		resultRosMessage.getSocialContext().setName(socialContext.getName());
		resultRosMessage.getSocialContext().setResult("completion");
		
		return resultRosMessage;
	}

}
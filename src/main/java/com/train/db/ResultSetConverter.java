package com.train.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.train.db.repository.entities.CategoryEntity;
import com.train.db.repository.entities.DistributorEntity;
import com.train.db.repository.entities.OrderDetailsEntity;
import com.train.db.repository.entities.OrderEntity;
import com.train.db.repository.entities.ProductEntity;
import com.train.db.repository.entities.ReportEntity;
import com.train.db.repository.entities.StaffEntity;
import com.train.enumerations.Qualification;
import com.train.enumerations.Role;
import com.train.enumerations.Status;

/**
 * Util class which contains only static methods and help transform 
 * the data of table from {@link java.sql.ResultSet} to entities.
 */
public final class ResultSetConverter {

	private ResultSetConverter() {
	}

	public static DistributorEntity createDistributorEntity(ResultSet set) throws SQLException {
		DistributorEntity distributor = new DistributorEntity();
		
		distributor.setContractId(set.getInt("contract_id"));
		distributor.setFirstName(set.getString("dist_first_name"));
		distributor.setSecondName(set.getString("dist_second_name"));
		distributor.setEmail(set.getString("dist_email"));
		distributor.setPassword(set.getString("dist_password"));
		distributor.setRole(Role.parseInt(set.getInt("role_id")));
		distributor.setAge(set.getInt("dist_age"));
		distributor.setRegistrationDate(set.getDate("dist_registration_date").toLocalDate());
		distributor.setQualification(Qualification.valueOf(set.getString("qualification")));
		distributor.setLeaderContractId(set.getInt("leader_contract_id"));
		distributor.setMoney(set.getDouble("dist_money"));

		return distributor;
	}
	
	public static StaffEntity createStaffEntity(ResultSet set) throws SQLException {
		StaffEntity staff = new StaffEntity();
		
		staff.setContractId(set.getInt("contract_id"));
		staff.setFirstName(set.getString("staff_first_name"));
		staff.setSecondName(set.getString("staff_second_name"));
		staff.setEmail(set.getString("staff_email"));
		staff.setPassword(set.getString("staff_password"));
		staff.setRole(Role.parseInt(set.getInt("role_id")));
		staff.setAge(set.getInt("staff_age"));
		staff.setRegistrationDate(set.getDate("staff_registration_date").toLocalDate());

		return staff;
	}
	
	public static CategoryEntity createCategoryEntity(ResultSet set) throws SQLException {
		CategoryEntity category = new CategoryEntity();
		
		category.setId(set.getInt("category_id"));
		category.setTitle(set.getString("category_title"));
		category.setDescription(set.getString("category_description"));
		category.setStatus(Status.parseInt(set.getInt("status_id")));

		return category;
	}
	
	public static ProductEntity createProductEntity(ResultSet set) throws SQLException {
		ProductEntity product = new ProductEntity();
		
		product.setId(set.getInt("product_id"));
		product.setTitle(set.getString("product_title"));
		product.setDescription(set.getString("product_description"));
		product.setPrice(set.getDouble("product_price"));
		product.setQuantity(set.getInt("product_quantity"));
		product.setBonus(set.getInt("product_bonus"));
		product.setCategoryId(set.getInt("category_id"));
		product.setStatus(Status.parseInt(set.getInt("status_id")));

		return product;
	}
	
	public static ReportEntity createReportEntity(ResultSet set) throws SQLException {
		ReportEntity report = new ReportEntity();
		
		report.setId(set.getInt("report_id"));
		report.setStartDate(set.getDate("start_date").toLocalDate());
		report.setEndDate(set.getDate("end_date").toLocalDate());
		report.setBonus(set.getDouble("bonus"));
		report.setPersonalPoints(set.getInt("personal_points"));
		report.setPersonalGroupPoints(set.getInt("personal_group_points"));
		report.setGroupPoints(set.getInt("group_points"));
		report.setContractId(set.getInt("contract_id"));

		return report;
	}
	
	public static List<OrderEntity> createListOrderEntities(ResultSet set) throws SQLException {
		List<OrderEntity> listOrders = new ArrayList<>();
		
		while (set.next()) {
			OrderDetailsEntity detail = createOrderDetailsEntity(set);
			OrderEntity order;
			if (listOrders.size() > 0) {
				order = listOrders.get(listOrders.size() - 1);
				if (order.getId() == detail.getOrderId()) {
					order.addDetailList(detail);
				} else {
					order = createOrderEntity(set);
					order.addDetailList(detail);
					listOrders.add(order);
				}
			} else {
				order = createOrderEntity(set);
				order.addDetailList(detail);
				listOrders.add(order);
			}
		}
		
		return listOrders;
	}
	
	public static OrderEntity createOrderEntity(ResultSet set) throws SQLException {
		OrderEntity order = new OrderEntity();
		
		order.setId(set.getInt("order_id"));
		order.setOrderDate(set.getDate("order_date").toLocalDate());
		order.setContractId(set.getInt("contract_id"));
		order.setTotalPrice(set.getDouble("total_price"));
		order.setTotalBonus(set.getInt("total_bonus"));
		
		List<OrderDetailsEntity> listDetails = new ArrayList<>();
		order.setListDetails(listDetails);
		
		return order;
	}
	
	public static OrderDetailsEntity createOrderDetailsEntity(ResultSet set) throws SQLException {
		OrderDetailsEntity details = new OrderDetailsEntity();
		
		details.setId(set.getInt("details_id"));
		details.setOrderId(set.getInt("order_id"));
		details.setProductId(set.getInt("product_id"));
		details.setQuantityOrdered(set.getInt("quantity_ordered"));
		details.setSumPrice(set.getDouble("sum_price"));
		details.setSumBonus(set.getInt("sum_bonus"));
		
		return details;
	}
}


package com.bootx.mall.auth.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.dao.MessageDao;
import com.bootx.mall.auth.dao.MessageGroupDao;
import com.bootx.mall.auth.entity.Message;
import com.bootx.mall.auth.entity.MessageGroup;
import com.bootx.mall.auth.entity.MessageStatus;
import com.bootx.mall.auth.entity.User;
import com.bootx.mall.auth.service.MessageGroupService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 消息组
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class MessageGroupServiceImpl extends BaseServiceImpl<MessageGroup, Long> implements MessageGroupService {

	@Inject
	private MessageGroupDao messageGroupDao;
	@Inject
	private MessageDao messageDao;

	@Override
	@Transactional(readOnly = true)
	public Page<MessageGroup> findPage(User user, Pageable pageable) {
		return messageGroupDao.findPage(user, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public MessageGroup find(User user1, User user2) {
		return messageGroupDao.find(user1, user2);
	}

	@Override
	public void delete(MessageGroup messageGroup, User user) {
		Assert.notNull(messageGroup, "[Assertion failed] - messageGroup is required; it must not be null");
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");

		MessageStatus user1MessageStatus = messageGroup.getUser1MessageStatus();
		MessageStatus user2MessageStatus = messageGroup.getUser2MessageStatus();
		if (user.equals(messageGroup.getUser1())) {
			user1MessageStatus.setIsDeleted(true);
		} else {
			user2MessageStatus.setIsDeleted(true);
		}
		if (BooleanUtils.isTrue(user1MessageStatus.getIsDeleted()) && BooleanUtils.isTrue(user2MessageStatus.getIsDeleted())) {
			messageGroupDao.remove(messageGroup);
		}

		List<Message> messages = messageDao.findList(messageGroup, user);
		for (Message message : messages) {
			MessageStatus fromUserMessageStatus = message.getFromUserMessageStatus();
			MessageStatus toUserMessageStatus = message.getToUserMessageStatus();
			if (user.equals(message.getFromUser())) {
				fromUserMessageStatus.setIsDeleted(true);
			} else if (user.equals(message.getToUser())) {
				toUserMessageStatus.setIsDeleted(true);
			}
			if (BooleanUtils.isTrue(fromUserMessageStatus.getIsDeleted()) && BooleanUtils.isTrue(toUserMessageStatus.getIsDeleted())) {
				messageDao.remove(message);
			}
		}
	}

}
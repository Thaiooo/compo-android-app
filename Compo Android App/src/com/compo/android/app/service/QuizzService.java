package com.compo.android.app.service;

import java.util.Date;

import android.content.Context;

import com.compo.android.app.dao.MatchProgressDao;
import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.PackProgressDao;
import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.dao.QuizzPlayerDao;
import com.compo.android.app.model.MatchProgress;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class QuizzService {

    private Context _context;

    public QuizzService(Context context) {
	_context = context;
    }

    // =========================================
    // TODO Ceci doit etre dans une transaction
    // =========================================
    public ServiceResultSave saveSuccessResponse(QuizzPlayer aQuizzPlayer, Play aPlay, String aResponse) {

	ServiceResultSave result = new ServiceResultSave();

	// MAJ du user
	User user = UserFactory.getInstance().getUser(_context);
	user.setCredit(user.getCredit() + aQuizzPlayer.getEarnCredit());
	UserFactory.getInstance().updateUser(_context);

	// MAJ du play
	if (aPlay == null) {
	    aPlay = new Play();
	    aPlay.setQuizzId(aQuizzPlayer.getId());
	    aPlay.setUserId(user.getId());
	    aPlay.setUnlockHint(false);
	    aPlay.setUnlockRandom(false);
	    aPlay.setUnlock50Percent(false);
	    aPlay.setUnlockResponse(false);
	}
	aPlay.setDateTime(new Date());
	aPlay.setResponse(aResponse);
	result.setPlay(aPlay);

	PlayDao dao = new PlayDao(_context);
	if (aPlay.getId() == 0) {
	    dao.add(aPlay);
	} else {
	    dao.update(aPlay);
	}

	QuizzPlayerDao qpDao = new QuizzPlayerDao(_context);
	int nbQuizz = qpDao.countHidePlayerForMatch(aQuizzPlayer.getMatch().getId());

	MatchProgressDao mpDao = new MatchProgressDao(_context);
	MatchProgress mathProgress = mpDao.find(aQuizzPlayer.getMatch().getId());
	int nbOfSuccessQuizz = 0;
	if (mathProgress != null) {
	    nbOfSuccessQuizz = mathProgress.getNumberOfSuccessQuizz();
	    mathProgress.setNumberOfSuccessQuizz(nbOfSuccessQuizz + 1);
	    mpDao.add(mathProgress);
	} else {
	    mathProgress = new MatchProgress();
	    mathProgress.setMatch(aQuizzPlayer.getMatch());
	    mathProgress.setNumberOfSuccessQuizz(1);
	    mpDao.update(mathProgress);
	}

	PackDao packDao = new PackDao(_context);
	Pack pack = packDao.findPackLightByMatch(aQuizzPlayer.getMatch().getId());

	boolean isAllQuizzSuccess = false;
	// MAJ du pack progress
	if (nbQuizz == nbOfSuccessQuizz + 1) {
	    PackProgressDao packProgressDao = new PackProgressDao(_context);
	    PackProgress progress = packProgressDao.find(pack);
	    if (progress == null) {
		progress = new PackProgress();
		progress.setNumberOfSuccessMatch(1);
		progress.setPack(pack);
		packProgressDao.add(progress);
	    } else {
		progress.setNumberOfSuccessMatch(progress.getNumberOfSuccessMatch() + 1);
		packProgressDao.update(progress);
	    }
	    isAllQuizzSuccess = true;
	}
	result.setAllQuizzSuccess(isAllQuizzSuccess);

	return result;

    }

    // =========================================
    // TODO Ceci doit etre dans une transaction
    // =========================================
    public Play saveInccorectResponse(QuizzPlayer aQuizzPlayer, Play aPlay, String aResponse) {
	PlayDao dao = new PlayDao(_context);
	if (aPlay == null) {
	    aPlay = new Play();
	    aPlay.setQuizzId(aQuizzPlayer.getId());
	    aPlay.setUserId(UserFactory.getInstance().getUser(_context).getId());
	    aPlay.setUnlockHint(false);
	    aPlay.setUnlockRandom(false);
	    aPlay.setUnlock50Percent(false);
	    aPlay.setUnlockResponse(false);
	}
	aPlay.setDateTime(new Date());
	aPlay.setResponse(aResponse);

	if (aPlay.getId() == 0) {
	    dao.add(aPlay);
	} else {
	    dao.update(aPlay);
	}

	MatchProgressDao mpDao = new MatchProgressDao(_context);
	MatchProgress mathProgress = mpDao.find(aQuizzPlayer.getMatch().getId());
	int nbOfSuccessQuizz = 0;
	if (mathProgress != null) {
	    nbOfSuccessQuizz = mathProgress.getNumberOfSuccessQuizz();
	    mathProgress.setNumberOfSuccessQuizz(nbOfSuccessQuizz + 1);
	    mpDao.add(mathProgress);
	} else {
	    mathProgress = new MatchProgress();
	    mathProgress.setMatch(aQuizzPlayer.getMatch());
	    mathProgress.setNumberOfSuccessQuizz(1);
	    mpDao.update(mathProgress);
	}

	return aPlay;
    }

    // =========================================
    // TODO Ceci doit etre dans une transaction
    // =========================================
    public void resetAllUserData() {
	PlayDao dao = new PlayDao(_context);
	dao.eraseAll();

	PackProgressDao progressDao = new PackProgressDao(_context);
	progressDao.eraseAll();

	MatchProgressDao matchProgressDao = new MatchProgressDao(_context);
	matchProgressDao.eraseAll();

	User user = UserFactory.getInstance().getUser(_context);
	user.setOverallTime(0);
	user.setCredit(0);

	UserFactory.getInstance().updateUser(_context);
    }

    public Play savePlay(Play aPlay) {
	PlayDao dao = new PlayDao(_context);
	if (aPlay.getId() == 0) {
	    dao.add(aPlay);
	} else {
	    dao.update(aPlay);
	}
	return aPlay;
    }

}

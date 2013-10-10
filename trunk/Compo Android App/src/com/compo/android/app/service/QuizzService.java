package com.compo.android.app.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.dao.DataBaseHelper;
import com.compo.android.app.dao.MatchDao;
import com.compo.android.app.dao.MatchProgressDao;
import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.PackProgressDao;
import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.dao.QuizzPlayerDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.MatchProgress;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class QuizzService {

    private Context _context;
    private DataBaseHelper _dataBaseHeleper;

    public QuizzService(Context context) {
	_context = context;
	_dataBaseHeleper = new DataBaseHelper(context);
    }

    public ServiceResultSave saveSuccessResponse(QuizzPlayer aQuizzPlayer, Play aPlay, String aResponse) {
	ServiceResultSave result = new ServiceResultSave();

	_dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = _dataBaseHeleper.getWritableDatabase();
	    session.beginTransaction();

	    // ---------------------------------------------------------------------------------------------------------
	    // MAJ du user
	    // ---------------------------------------------------------------------------------------------------------
	    User user = UserFactory.getInstance().getUser(session, _context);
	    user.setCredit(user.getCredit() + aQuizzPlayer.getEarnCredit());
	    UserFactory.getInstance().updateUser(session, _context);

	    // ---------------------------------------------------------------------------------------------------------
	    // MAJ du play
	    // ---------------------------------------------------------------------------------------------------------
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
		dao.add(session, aPlay);
	    } else {
		dao.update(session, aPlay);
	    }

	    // ---------------------------------------------------------------------------------------------------------
	    // MAJ du match progress
	    // ---------------------------------------------------------------------------------------------------------
	    QuizzPlayerDao qpDao = new QuizzPlayerDao(_context);
	    int nbQuizz = qpDao.countHidePlayerForMatch(session, aQuizzPlayer.getMatch().getId());

	    MatchProgressDao mpDao = new MatchProgressDao(_context);
	    MatchProgress mathProgress = mpDao.find(session, aQuizzPlayer.getMatch().getId());
	    int nbOfSuccessQuizz = 0;
	    if (mathProgress != null) {
		nbOfSuccessQuizz = mathProgress.getNumberOfSuccessQuizz() + 1;
	    } else {
		nbOfSuccessQuizz = 1;
		mathProgress = new MatchProgress();
		mathProgress.setMatch(aQuizzPlayer.getMatch());
	    }

	    boolean isAllQuizzSuccess = false;
	    if (nbQuizz == nbOfSuccessQuizz) {
		mathProgress.setCompleted(true);

		// ---------------------------------------------------------------------------------------------------------
		// MAJ du pack progress
		// ---------------------------------------------------------------------------------------------------------
		PackDao packDao = new PackDao(_context);
		Pack pack = packDao.findPackLightByMatch(session, aQuizzPlayer.getMatch().getId());
		PackProgressDao packProgressDao = new PackProgressDao(_context);
		PackProgress packProgress = packProgressDao.find(session, pack);
		if (packProgress == null) {
		    packProgress = new PackProgress();
		    packProgress.setNumberOfSuccessMatch(1);
		    packProgress.setPack(pack);
		    packProgressDao.add(session, packProgress);
		} else {
		    packProgress.setNumberOfSuccessMatch(packProgress.getNumberOfSuccessMatch() + 1);
		    packProgressDao.update(session, packProgress);
		}
		isAllQuizzSuccess = true;
	    } else {
		mathProgress.setCompleted(false);
	    }
	    mathProgress.setNumberOfSuccessQuizz(nbOfSuccessQuizz);

	    if (mathProgress.getId() == 0) {
		mpDao.add(session, mathProgress);
	    } else {
		mpDao.update(session, mathProgress);
	    }

	    result.setAllQuizzSuccess(isAllQuizzSuccess);

	    session.setTransactionSuccessful();
	} finally {
	    if (session != null) {
		session.endTransaction();
		session.close();
	    }
	    _dataBaseHeleper.close();
	}

	return result;

    }

    public Play saveInccorectResponse(QuizzPlayer aQuizzPlayer, Play aPlay, String aResponse) {
	_dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = _dataBaseHeleper.getWritableDatabase();
	    session.beginTransaction();

	    // ---------------------------------------------------------------------------------------------------------
	    // MAJ du play
	    // ---------------------------------------------------------------------------------------------------------
	    PlayDao dao = new PlayDao(_context);
	    if (aPlay == null) {
		aPlay = new Play();
		aPlay.setQuizzId(aQuizzPlayer.getId());
		aPlay.setUserId(UserFactory.getInstance().getUser(session, _context).getId());
		aPlay.setUnlockHint(false);
		aPlay.setUnlockRandom(false);
		aPlay.setUnlock50Percent(false);
		aPlay.setUnlockResponse(false);
	    }
	    aPlay.setDateTime(new Date());
	    aPlay.setResponse(aResponse);

	    if (aPlay.getId() == 0) {
		dao.add(session, aPlay);
	    } else {
		dao.update(session, aPlay);
	    }

	    // ---------------------------------------------------------------------------------------------------------
	    // MAJ du match progress
	    // ---------------------------------------------------------------------------------------------------------
	    MatchProgressDao mpDao = new MatchProgressDao(_context);
	    MatchProgress mathProgress = mpDao.find(session, aQuizzPlayer.getMatch().getId());
	    if (mathProgress == null) {
		mathProgress = new MatchProgress();
		mathProgress.setMatch(aQuizzPlayer.getMatch());
		mathProgress.setNumberOfSuccessQuizz(0);
		mathProgress.setCompleted(false);
		mpDao.add(session, mathProgress);
	    }

	    session.setTransactionSuccessful();
	} finally {
	    if (session != null) {
		session.endTransaction();
		session.close();
	    }
	    _dataBaseHeleper.close();
	}

	return aPlay;
    }

    public void resetAllUserData() {
	_dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = _dataBaseHeleper.getWritableDatabase();
	    session.beginTransaction();

	    PlayDao dao = new PlayDao(_context);
	    dao.eraseAll(session);

	    PackProgressDao progressDao = new PackProgressDao(_context);
	    progressDao.eraseAll(session);

	    MatchProgressDao matchProgressDao = new MatchProgressDao(_context);
	    matchProgressDao.eraseAll(session);

	    User user = UserFactory.getInstance().getUser(session, _context);
	    user.setOverallTime(0);
	    user.setCredit(0);

	    UserFactory.getInstance().updateUser(session, _context);

	    session.setTransactionSuccessful();
	} finally {
	    if (session != null) {
		session.endTransaction();
		session.close();
	    }
	    _dataBaseHeleper.close();
	}

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

    public Match getNexMatch(Pack aPack, Match aCurrentMatch) {
	MatchDao matchDao = new MatchDao(_context);
	List<Match> matchs = matchDao.getUncompletedMatchsByPack(aPack);
	if (matchs.isEmpty()) {
	    return null;
	}

	Match next = null;
	for (Match m : matchs) {
	    if (m.getOrderNumber() > aCurrentMatch.getOrderNumber()) {
		next = m;
		break;
	    }
	}

	if (next == null) {
	    // Trier dans l'ordre inverse. Plus grand au plus petit
	    Collections.sort(matchs, new Comparator<Match>() {
		@Override
		public int compare(Match aMatch1, Match aMatch2) {

		    if (aMatch1.getOrderNumber() > aMatch2.getOrderNumber()) {
			return 1;
		    } else if (aMatch1.getOrderNumber() == aMatch2.getOrderNumber()) {
			return 0;
		    } else {
			return -1;
		    }
		}
	    });

	    for (Match m : matchs) {
		if (m.getOrderNumber() < aCurrentMatch.getOrderNumber()) {
		    next = m;
		    break;
		}
	    }
	}

	return next;
    }
}
